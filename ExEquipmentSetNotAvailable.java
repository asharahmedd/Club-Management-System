public class ExEquipmentSetNotAvailable  extends Exception{
    public ExEquipmentSetNotAvailable(String messaege){
        super(messaege);
    }
    public  ExEquipmentSetNotAvailable(){
        super("There is no available set of this equipment for the command.");
    }
}
