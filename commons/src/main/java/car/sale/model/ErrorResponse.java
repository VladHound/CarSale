package car.sale.model;

import java.time.OffsetDateTime;

public record ErrorResponse(String id, String message, OffsetDateTime timestamp) {
}
