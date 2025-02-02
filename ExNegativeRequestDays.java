public class ExNegativeRequestDays extends Exception {
    public ExNegativeRequestDays(String message){
        super(message);
    }
    public ExNegativeRequestDays(){
        super("The number of days must be at least 1.");
    }
    
    
}
