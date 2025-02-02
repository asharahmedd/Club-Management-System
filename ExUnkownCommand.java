public class ExUnkownCommand extends Exception {
    public ExUnkownCommand() {
        super("Unknown command - ignored.");

    }
    public ExUnkownCommand(String message){
        super(message);
    }
}
