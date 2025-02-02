
public class ExEquipmentRecordMissing extends Exception {
public ExEquipmentRecordMissing(String message){
super("Missing record for Equipment "+message+". Cannot mark this item arrival.");
}
public ExEquipmentRecordMissing(){
    super();
}
}
