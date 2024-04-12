package car.sale.mailsender.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import car.sale.mailsender.model.UserMailRequest;

@Aspect
@Slf4j
@Component
public class MailLoggingAspect {

    private static final String TEMPLATE_BEFORE = "-".repeat(50);

    @Pointcut("execution(public void sendTokenToUser(..))")
    public void logSendToken() {
    }

    @Pointcut("execution(public void buildCodeActivationMessage(..))")
    public void logBuildMailIfThrowing() {
    }

    @Before("logSendToken()")
    public void logActivationEmailSent(JoinPoint point) {

        if (point.getSignature().getName().equals("sendTokenToUser")) {
            Object[] args = point.getArgs();

            if (args.length == 2 && args[0] instanceof UserMailRequest user) {
                log.info(TEMPLATE_BEFORE);
                log.info(
                        "Attempting to send a user with email: '%s' a secret token for account activation"
                                .formatted(user.email())
                );

                if (user.isActive()) {
                    log.info("User with email: '%s' already activated".formatted(user.email()));
                }
            }
        }
    }

    @AfterThrowing(pointcut = "logBuildMailIfThrowing()", throwing = "exception")
    public void logActivationFailedThrowingTemplate(Throwable exception) {
        log.info("Failed to load the message template for sending the token: " + exception);
    }
}
