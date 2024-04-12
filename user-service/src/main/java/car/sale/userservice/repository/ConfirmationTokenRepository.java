package car.sale.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import car.sale.userservice.model.ConfirmationToken;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(UUID token);

    @Modifying
    @Query("""
            DELETE FROM ConfirmationToken ct
            WHERE ct.expiresAt < :dateTime
             """)
    void deleteByExpiresAtBefore(@Param("dateTime") LocalDateTime dateTime);
}
