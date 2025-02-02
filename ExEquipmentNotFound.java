public class ExEquipmentNotFound extends Exception {
    public ExEquipmentNotFound(String message) {
        super(message);
    }

    public ExEquipmentNotFound() {
        super("Equipment record not found.");
    }
}