package car.sale.userservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import car.sale.userservice.repository.ConfirmationTokenRepository;
import car.sale.userservice.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository tokenRepository;
    private final UserRepository userRepository;

    // Другие поля и конструктор

    public ConfirmationTokenService(ConfirmationTokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void deleteExpiredTokensAndUsers() {
        LocalDateTime now = LocalDateTime.now();
        tokenRepository.deleteByExpiresAtBefore(now);
        userRepository.deleteByCreatedAtBefore(now);
    }
}
