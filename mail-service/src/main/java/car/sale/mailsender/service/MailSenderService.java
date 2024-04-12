package car.sale.mailsender.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Async
    public void send(String emailTo, String subject, String message) {
        try {
            MimeMessage mime = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(mime, "utf-8");
            helper.setText(message, true);
            helper.setTo(emailTo);
            helper.setSubject(subject);
            helper.setFrom(emailFrom);

            mailSender.send(mime);
        } catch (MessagingException e) {
            log.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email");
        }
    }
}

