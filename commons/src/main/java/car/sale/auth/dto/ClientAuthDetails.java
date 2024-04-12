package car.sale.auth.dto;

import java.util.Set;
import java.util.UUID;

public record ClientAuthDetails(
        UUID uuid,
        String email,
        String password,
        String role,
        boolean isActive
) {
}