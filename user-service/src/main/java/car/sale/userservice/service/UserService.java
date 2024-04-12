package car.sale.userservice.service;

import car.sale.auth.dto.ClientAuthDetails;
import car.sale.auth.model.Role;
import car.sale.model.ErrorResponse;
import car.sale.userservice.dto.request.UserRegistrationDto;
import car.sale.userservice.dto.request.UserRequestDto;
import car.sale.userservice.dto.response.CreateUserResponse;
import car.sale.userservice.dto.response.UserResponseDto;
import car.sale.userservice.dto.response.UserToSellerResponse;
import car.sale.userservice.exception.UserAlreadyExistsException;
import car.sale.userservice.mapper.UserRequestMapper;
import car.sale.userservice.mapper.UserResponseMapper;
import car.sale.userservice.model.ConfirmationToken;
import car.sale.userservice.model.User;
import car.sale.userservice.model.UserCredentials;
import car.sale.userservice.model.UserMailRequest;
import car.sale.userservice.repository.ConfirmationTokenRepository;
import car.sale.userservice.repository.UserRepository;
import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

import static car.sale.userservice.utils.ExceptionMessagesConstants.*;
import static car.sale.util.HttpUtils.PUBLIC_API_VI;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;
    private final ConfirmationTokenRepository repository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final Gson gson = new Gson();

    @Value("${services.gateway-service.url}")
    private String API_GATEWAY_URL;

    @Value("${spring.kafka.topic}")
    private String topic;

    private final WebClient webClient = WebClient.create();

    public CreateUserResponse signUp(UserRegistrationDto userDto) {
//        userRegistrationValidator.validate(userDto);

        checkEmailForUniqueness(userDto.email());

        User user = User.builder()
                .credentials(new UserCredentials(
                        userDto.credentials().lastName(),
                        userDto.credentials().firstName(),
                        userDto.credentials().patronymic())
                )
                .externalId(UUID.randomUUID())
                .role(Role.USER)
                .email(userDto.email())
                .password(userDto.password())
                .build();

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                UUID.fromString(token),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                user
        );

        sendRequestToMailSender(userRequestMapper.toMailRequest(user), token);

        CreateUserResponse createUserResponse = new CreateUserResponse(userRepository.save(user).externalId());

        repository.save(confirmationToken);

        return createUserResponse;
    }

    private void sendRequestToMailSender(UserMailRequest userRequest, String token) {
        log.info("Send activation code from user-service. \nPARAM: " + token + ", \nBODY: " + userRequest.toString());
        webClient.post()
                .uri(
                        UriComponentsBuilder
                                .fromHttpUrl("%s/%s/mail/send/activation".formatted(API_GATEWAY_URL, PUBLIC_API_VI))
                                .queryParam("token", token)
                                .build()
                                .toUri()
                )
                .header(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE
                )
                .body(BodyInserters.fromValue(userRequest))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @Transactional
    public void confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(UUID.fromString(token))
                .orElseThrow(
                        () -> new EntityNotFoundException("Token not found")
                );

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        User user = userRepository.findByExternalId(confirmationToken.getUser().externalId())
                .orElseThrow(
                        () -> new EntityNotFoundException("User not found by activation token: " + token)
                );

        user.isActive(true);
    }

    @Cacheable(value = "users", key = "#externalId")
    public UserResponseDto findUserByExternalId(UUID externalId) {
        val user = userRepository.findByExternalId(externalId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_ERROR_MESSAGE + externalId));
        return userResponseMapper.map(user);
    }

    @Transactional
    @CacheEvict(value = "users", key = "#externalId")
    public void deleteUserByExternalId(UUID externalId) {
        val user = userRepository.findByExternalId(externalId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_ERROR_MESSAGE + externalId));
        user.isDeleted(true);
    }

    @Transactional
    @CachePut(value = "users", key = "#externalId")
    public UserResponseDto update(UUID externalId, UserRequestDto userRequestDto) {
        val storedUser = userRepository.findByExternalId(externalId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_ERROR_MESSAGE + externalId));

        val updatedUser = userRequestMapper.map(userRequestDto);

        setEmailIfChangedAndRemainedUnique(storedUser, updatedUser);

        return userResponseMapper.map(storedUser);
    }

    public ClientAuthDetails getUserDetails(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_BY_EMAIL_ERROR_MESSAGE + email));
    }

    private void checkEmailForUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(
                    String.valueOf(
                            new ErrorResponse(
                                    UUID.randomUUID().toString(),
                                    USER_WITH_THE_SAME_EMAIL_IS_EXISTS_MESSAGE.formatted(email),
                                    OffsetDateTime.now())
                    )
            );
        }
    }

    private void setEmailIfChangedAndRemainedUnique(User storedUser, User updatedUser) {
        val email = updatedUser.email();
        if (ObjectUtils.notEqual(storedUser.email(), email)) {
            checkEmailForUniqueness(email);
            storedUser.email(email);
        }
    }

    @Transactional
    public void setToUserSellerRole(UUID userId) {
        User user = userRepository.findByExternalId(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_ERROR_MESSAGE + userId));

        if (user.role() == Role.SELLER) {
            log.info("User already seller");
            return;
        }

        user.role(Role.SELLER);

        UserToSellerResponse response = new UserToSellerResponse(userId, user.email(), user.password(),
                user.credentials().getFirstName(), user.credentials().getLastName(),
                user.credentials().getPatronymic(), user.photoId()
        );

        System.out.println(response);

        kafkaTemplate.send(topic, gson.toJson(response));
    }
}