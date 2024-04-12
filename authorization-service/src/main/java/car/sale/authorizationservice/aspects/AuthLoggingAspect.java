package car.sale.authorizationservice.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import car.sale.authorizationservice.dto.UserLoginRequest;

@Aspect
@Slf4j
@Component
public class AuthLoggingAspect {

    @Pointcut("execution(* login(..))")
    public void logUserLogin() {
    }
    @Pointcut("execution(* revokeAuth(..))")
    public void logRevokeAuth() {
    }

    @Before("logUserLogin()")
    public void logUserLoginPostMethod(JoinPoint point) {

        if (point.getSignature().getName().equals("login")) {
            Object[] args = point.getArgs();

            if (args[0] instanceof UserLoginRequest request) {
                log.info(
                        "Attempting to log in with email: '%s'"
                                .formatted(request.email())
                );
            }
        }
    }
}
