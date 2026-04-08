// Custom checked exception for unauthorized access attempts
public class UnauthorisedAccessException extends Exception {
    public UnauthorisedAccessException(String message) {
        super(message);
    }
}