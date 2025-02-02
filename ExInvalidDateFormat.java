public class ExInvalidDateFormat extends Exception {
    public ExInvalidDateFormat(String message){
         super(message);
    }
    public ExInvalidDateFormat(){
        super("Invalid date.");
   }
}
