package car.sale.userservice.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class UserLoggingAspect {
    @Before("execution(* car.sale.userservice.controller.publicapi.UserController.create(..))")
    public void beforeCreateUser(JoinPoint point) {
        Object[] args = point.getArgs();

        log.info(
                "Attempting to create a new user with the following fields: {%n%s%n}".formatted(args)
        );
    }
}
