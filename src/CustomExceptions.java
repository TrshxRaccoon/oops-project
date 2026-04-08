// All custom exceptions in one class for convenience
public class CustomExceptions {

    // Thrown when a resident is not found in the system
    public static class ResidentNotFoundException extends Exception {
        public ResidentNotFoundException(String message) { super(message); }
    }

    // Thrown when a room does not exist
    public static class RoomNotFoundException extends Exception {
        public RoomNotFoundException(String message) { super(message); }
    }

    // Thrown when a room has no available slots
    public static class RoomFullException extends Exception {
        public RoomFullException(String message) { super(message); }
    }

    // Thrown for invalid payment operations
    public static class PaymentException extends Exception {
        public PaymentException(String message) { super(message); }
    }
}