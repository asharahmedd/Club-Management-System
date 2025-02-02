
public class ExOverlappingRequestAndBorrow extends Exception {


    public ExOverlappingRequestAndBorrow(String message){
        super(message);
    }
    public ExOverlappingRequestAndBorrow(){
        super("The period overlaps with a current period that the member borrows / requests  the equipment.");
    }
}
