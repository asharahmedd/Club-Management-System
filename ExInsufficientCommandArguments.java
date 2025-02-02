public class ExInsufficientCommandArguments extends Exception{
    public ExInsufficientCommandArguments(String message) {
        super(message);
    }
    public ExInsufficientCommandArguments() {
        super("Insufficient command arguments.");
    }
}