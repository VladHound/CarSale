package car.sale.userservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import car.sale.mapper.Mapper;
import car.sale.userservice.dto.response.UserResponseDto;
import car.sale.userservice.model.User;

@Component
@RequiredArgsConstructor
public class UserResponseMapper implements Mapper<User, UserResponseDto> {

    // TODO: исправить
    @Override
    public UserResponseDto map(User user) {
        return new UserResponseDto(
                user.externalId(),
                user.credentials().getFirstName(),
                user.credentials().getLastName(),
                user.email(),
                user.photoId()
        );
    }
}