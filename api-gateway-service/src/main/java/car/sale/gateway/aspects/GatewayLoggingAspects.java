package car.sale.gateway.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Aspect
@Slf4j
@Component
public class GatewayLoggingAspects {
    @Pointcut("execution(* authenticate*(..))")
    private void loggingAuthenticateUser() {
    }

    private static final String TEMPLATE = "-".repeat(50);

    @Before("loggingAuthenticateUser()")
    public void beforeAuthenticateUser(JoinPoint point) {

        Object arg = point.getArgs()[0];

        if (arg instanceof Mono<?> loginRequest) {
            log.info(TEMPLATE);
            log.info(
                    "An attempt is made to authenticate the user: {%n\t%s%n}".formatted(loginRequest)
            );
        }

    }
}
