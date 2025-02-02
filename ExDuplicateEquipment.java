public class ExDuplicateEquipment extends Exception{
    public ExDuplicateEquipment(String message) {
        super(message);

    }
    public ExDuplicateEquipment(Equipment equipment) {
        super("Equipment code already in use: "+ equipment.getCode() +" "+ equipment.getName() );

    }
}
