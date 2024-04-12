package car.sale.userservice.model;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import car.sale.userservice.service.ConfirmationTokenService;

@Component
public class TokenCleanupScheduler {

    private final ConfirmationTokenService tokenService;

    public TokenCleanupScheduler(ConfirmationTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Scheduled(cron = "0 */10 * * * ?") // Запускать каждые 10 минут
    public void cleanupExpiredTokensAndUsers() {
        System.out.println("CHECK DELETE TOKEN");
        tokenService.deleteExpiredTokensAndUsers();
    }
}

