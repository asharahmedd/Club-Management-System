public class CmdEquipmentArrived extends RecordedCommand {
	String EquipmentId;
	@Override
	public void execute(String[] cmdParts) throws ExEquipmentNotFound, ExEquipmentRecordMissing {

		EquipmentId = cmdParts[1];
		if (Club.getInstance().checkexistingequipment(EquipmentId) == true) {
			Club.getInstance().addequipment(EquipmentId); //add the equipment
			addUndoCommand(this); 
			clearRedoList(); 
			System.out.println("Done.");
		} else {
			Equipment equipmentobj = Club.getInstance().findequipment(EquipmentId); //throw error if equipment is missing
			if (equipmentobj == null) {
				throw new ExEquipmentRecordMissing(EquipmentId);
			}
		}
	}

	//undo
	@Override
	public void undoMe() {
		Club.getInstance().removeequipment(EquipmentId);
		addRedoCommand(this); 
	}

	//redo
	@Override
	public void redoMe() {
		Club.getInstance().addequipment(EquipmentId);
		addUndoCommand(this); 
	}
}
