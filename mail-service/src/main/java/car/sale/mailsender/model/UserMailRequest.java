package car.sale.mailsender.model;

import java.util.UUID;

public record UserMailRequest(
        UUID externalId,
        String firstName,
        String lastName,
        String email,
        boolean isActive) {
}
