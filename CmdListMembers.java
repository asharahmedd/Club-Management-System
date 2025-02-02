public class CmdListMembers extends RecordedCommand{
    //We add instance fields in the objects to store the data which will be needed upon undo/redo
	
	@Override
	public void execute(String[] cmdParts)
	{
        Club.getInstance().listClubMembers();
        //addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
		//clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.

	}
	
	@Override
	public void undoMe()
	{
		//addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
		//addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}
