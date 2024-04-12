package car.sale.exception;

public class UserNotVerifiedYetException extends RuntimeException {

    public UserNotVerifiedYetException(String message) {
        super(message);
    }
}