package car.sale.userservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import car.sale.mapper.Mapper;
import car.sale.userservice.dto.request.UserRequestDto;
import car.sale.userservice.model.User;
import car.sale.userservice.model.UserMailRequest;

import java.util.UUID;

// TODO: Исправить в будущем
@Component
@RequiredArgsConstructor
public class UserRequestMapper implements Mapper<UserRequestDto, User> {


    @Override
    public User map(UserRequestDto userRequestDto) {
        return null;
//        return new User()
//                .externalId(UUID.randomUUID())
//                .firstName(userRequestDto.firstName())
//                .lastName(userRequestDto.lastName())
//                .phone(userRequestDto.phone())
//                .email(userRequestDto.email())
//                .login(trimEmailForLogin(userRequestDto))
//                .photoId(userRequestDto.photoId());
    }

    public UserMailRequest toMailRequest(User user) {
        return UserMailRequest.builder()
                .externalId(UUID.randomUUID())
                .firstName(user.credentials().getFirstName())
                .lastName(user.credentials().getLastName())
                .email(user.email())
                .isActive(user.isActive())
                .build();
    }

    private String trimEmailForLogin(UserRequestDto userRequestDto) {
        return userRequestDto.email().replaceAll("@[a-zA-Z0-9.-]+$", "");
    }
}