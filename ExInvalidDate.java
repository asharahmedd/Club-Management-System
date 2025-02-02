public class ExInvalidDate extends Exception {
    public ExInvalidDate(String message) {
        super(message);
    }
    public ExInvalidDate(){
        super("Invalid new day. The new day has to be later than the current date " + SystemDate.getInstance().toString()+".");
    }
}