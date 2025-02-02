public class CmdCreateNewEquipment extends RecordedCommand {
	private Equipment equipment;
	@Override
	public void execute(String[] cmdParts) throws ExDuplicateEquipment {
		String EquipmentId = cmdParts[1];
		String EquipmentName = cmdParts[2];
		if (Club.getInstance().checkequipment(EquipmentId) == null) { 
			equipment = new Equipment(EquipmentId, EquipmentName); 
			Club.getInstance().createequipment(equipment);	//create a new equipment
			addUndoCommand(this);
			clearRedoList();
			System.out.println("Done.");
		} else {
			Equipment equipmentobj = Club.getInstance().findequipment(EquipmentId); //throw error if there is a duplicate equipment
			throw new ExDuplicateEquipment(equipmentobj);
		}
	}


	//undo
	@Override
	public void undoMe() {
		Club.getInstance().removeequipment(equipment);
		addRedoCommand(this);
	}

	//redo
	@Override
	public void redoMe() {
		Club.getInstance().createequipment(equipment);
		addUndoCommand(this);
	}
}
