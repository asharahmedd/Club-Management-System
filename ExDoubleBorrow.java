public class ExDoubleBorrow extends Exception {
    public ExDoubleBorrow(String message) {
        super(message);
    }
    public ExDoubleBorrow(){
        super("The member is currently borrowing a set of this equipment. He/she cannot borrow one more at the same time.");
    }
}