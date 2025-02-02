public class ExInavlidRequestDays extends Exception {
    public ExInavlidRequestDays(String message) {
        super(message);
    }
    public ExInavlidRequestDays(){
        super("Please provide an integer for the number of days.");
    }
}