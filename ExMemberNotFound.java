public class ExMemberNotFound extends Exception {
    public  ExMemberNotFound(String message) {
        super(message);

    }
    public ExMemberNotFound(){
        super("Member not found.");
    }
}

