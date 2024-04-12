package car.sale.userservice.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserMailRequest(UUID externalId, String firstName, String lastName, String email, boolean isActive) {}
