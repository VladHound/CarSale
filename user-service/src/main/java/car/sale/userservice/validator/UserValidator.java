package car.sale.userservice.validator;

import car.sale.userservice.config.properties.ErrorInfoProperties;
import car.sale.userservice.dto.request.UserRequestDto;
import car.sale.userservice.validator.enums.UserFieldValidation;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static car.sale.userservice.utils.PatternConstants.EMAIL_PATTERN;
import static car.sale.userservice.utils.PatternConstants.NAME_PATTERN;
import static car.sale.userservice.utils.ValidationCodeConstants.*;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final ErrorInfoProperties properties;
    private final ObjectMapper objectMapper;


    @SneakyThrows
    public void validate(@NotNull UserRequestDto userRequestDto) {
        List<String> exceptionMessages = new ArrayList<>();

        Arrays.stream(UserFieldValidation.values())
                .forEach(field -> field.consume(this, userRequestDto, exceptionMessages));

        if (!CollectionUtils.isEmpty(exceptionMessages)) {
            throw new ValidationException(objectMapper.writeValueAsString(exceptionMessages));
        }
    }

    public void validateName(@NotNull UserRequestDto userRequestDto, @NotNull List<String> exceptionMessages) {
        val firstName = userRequestDto.firstName();
        val lastName = userRequestDto.lastName();

        if (StringUtils.isNotBlank(firstName) && !NAME_PATTERN.matcher(firstName).matches()) {
            exceptionMessages
                    .add(properties.getMessageByErrorCode(INCORRECT_FIRST_NAME_VALIDATION_ERROR_CODE)
                            .formatted(firstName));
        }

        if (StringUtils.isNotBlank(lastName) && !NAME_PATTERN.matcher(lastName).matches()) {
            exceptionMessages
                    .add(properties.getMessageByErrorCode(INCORRECT_LAST_NAME_VALIDATION_ERROR_CODE)
                            .formatted(lastName));
        }
    }

    public void validateEmail(@NotNull UserRequestDto userRequestDto, @NotNull List<String> exceptionMessages) {
        val email = userRequestDto.email();

        if (StringUtils.isBlank(email)) {
            exceptionMessages.add(properties.getMessageByErrorCode(BLANK_EMAIL_VALIDATION_ERROR_CODE));
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            exceptionMessages.add(properties.getMessageByErrorCode(INCORRECT_EMAIL_VALIDATION_ERROR_CODE)
                    .formatted(email));
        }
    }
}