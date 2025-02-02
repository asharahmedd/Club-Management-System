
public class ExOverlappingRequest extends Exception {


    public ExOverlappingRequest(String message){
        super(message);
    }
    public ExOverlappingRequest(){
        super("The period overlaps with a current period that the member requests the equipment.");
    }
}
