public class CmdStartNewDay extends RecordedCommand {

	private String oldDay;
	private String newDay;

	@Override
	public void execute(String[] cmdParts) throws ExInvalidDate, ExInvalidDateFormat {
		newDay = cmdParts[1];
		oldDay = SystemDate.getInstance().toString();
		Day newDayObj = Day.fromString(newDay);
		Day oldDayObj = SystemDate.getInstance();

		if (Day.valid(newDayObj.getyear(), newDayObj.getmonth(), newDayObj.getday()) == false) {//throw an error if the date format is not valid
			throw new ExInvalidDateFormat();
		} else if (newDayObj.compareTo(oldDayObj) > 0) {
			SystemDate.getInstance().set(newDay);
			addUndoCommand(this); 
			clearRedoList(); 
			System.out.println("Done.");
		} else {
			throw new ExInvalidDate();//throw an error if the date is not greater than the current date.
		}
	}


	//undo
	@Override
	public void undoMe() {
		try {
			SystemDate.getInstance().set(oldDay.toString());
			addRedoCommand(this); 
		} catch (ExInvalidDateFormat e) {
			e.getMessage();
		}
	}


	//redo
	@Override
	public void redoMe() {
		try {
			SystemDate.getInstance().set(newDay.toString());
			addUndoCommand(this);
		} catch (ExInvalidDateFormat e) {
			e.getMessage();
		}
	}
}
