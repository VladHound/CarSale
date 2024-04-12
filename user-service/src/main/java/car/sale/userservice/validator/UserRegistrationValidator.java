package car.sale.userservice.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import car.sale.userservice.config.properties.ErrorInfoProperties;
import car.sale.userservice.dto.request.UserRegistrationDto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static car.sale.userservice.utils.PatternConstants.*;
import static car.sale.userservice.utils.ValidationCodeConstants.*;

@Component
@RequiredArgsConstructor
public class UserRegistrationValidator {

    private final ErrorInfoProperties properties;

    private final ObjectMapper objectMapper;
//    private final PatternConstantsProperties patterns;

    @SneakyThrows
    public void validate(UserRegistrationDto registraction) {
        List<String> exceptionMessages = new ArrayList<>();

        val password = registraction.password();

        if (StringUtils.isBlank(password)) {
            exceptionMessages.add(properties.getMessageByErrorCode(BLANK_PASSWORD_VALIDATION_ERROR_CODE));
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
//        } else if (!patterns.getPasswordPattern().matcher(password).matches()) {
            exceptionMessages.add(properties.getMessageByErrorCode(INCORRECT_PASSWORD_VALIDATION_ERROR_CODE)
                    .formatted(password));
        }

        val email = registraction.email();

        if (StringUtils.isBlank(email)) {
            exceptionMessages.add(properties.getMessageByErrorCode(BLANK_EMAIL_VALIDATION_ERROR_CODE));
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
//        } else if (!patterns.getEmailPattern().matcher(email).matches()) {
            exceptionMessages.add(properties.getMessageByErrorCode(INCORRECT_EMAIL_VALIDATION_ERROR_CODE)
                    .formatted(email));
        }

        String lastName = registraction.credentials().lastName(),
                firstName = registraction.credentials().firstName(),
                patronymic = registraction.credentials().patronymic();

        Pattern namePattern = NAME_PATTERN;
//        Pattern namePattern = patterns.getNamePattern();

        if (StringUtils.isNotBlank(lastName) &&
                StringUtils.isNotBlank(firstName)) {
            exceptionMessages.add(properties.getMessageByErrorCode(BLANK_NAME_VALIDATION_ERROR_CODE));
        } else if (!namePattern.matcher(lastName).matches() &&
                !namePattern.matcher(firstName).matches() &&
                (patronymic.isBlank() || !namePattern.matcher(patronymic).matches())) {
            exceptionMessages.add(properties.getMessageByErrorCode(INCORRECT_NAME_VALIDATION_ERROR_CODE));
        }

        // TODO: вернуть валидацию позже
        if (CollectionUtils.isEmpty(exceptionMessages)) {
            throw new ValidationException(objectMapper.writeValueAsString(exceptionMessages));
        }
    }
}
