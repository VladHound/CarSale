package car.sale.mailsender.service;

import car.sale.mailsender.model.UserMailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import static car.sale.util.HttpUtils.PUBLIC_API_VI;

@Slf4j
@Service
//@RefreshScope
@RequiredArgsConstructor
public class RegistrationSenderService {

    @Autowired
    private final ResourceLoader resourceLoader;

    @Autowired
    private final MailSenderService mailSenderService;

    @Value("${message-mail}")
    private String mailMessageTemplate;

    @Value("${ms.api-gateway-service.url}")
    private String API_GATEWAY_URL;

    private String buildCodeActivationMessage(String name, String link) {
        return String.format(
                mailMessageTemplate,
                name, link, link, link
        );
    }

    public void sendTokenToUser(UserMailRequest user, String token) {
        if (!user.isActive()) {
            String link = "%s/%s/users/activation?token=%s".formatted(API_GATEWAY_URL, PUBLIC_API_VI, token);

            String buildMessage = buildCodeActivationMessage(user.firstName(), link);

            if (buildMessage != null && !mailMessageTemplate.isEmpty()) {
                mailSenderService.send(user.email(), "Активация пользователя", buildMessage);
            }
        }
    }
}
