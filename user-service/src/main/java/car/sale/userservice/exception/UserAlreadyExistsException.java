package car.sale.userservice.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public static final int STATUS_CODE = 409;

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
