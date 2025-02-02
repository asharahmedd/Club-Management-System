
public class ExSetIsNotAvailable extends Exception{
    public ExSetIsNotAvailable(String message){
        super(message);
    }
    public ExSetIsNotAvailable(){
        super("There is no available set of this equipment for the command.");
    }
}
