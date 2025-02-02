public class CmdRegister extends RecordedCommand {
	private Member member;

	@Override
	public void execute(String[] cmdParts) throws ExDuplicateMemberID {
		String memberId = cmdParts[1];
		String memberName = cmdParts[2];

		if (Club.getInstance().checkmember(memberId) == null) {
			member = new Member(memberId, memberName);
			Club.getInstance().addMember(member);//add the new nember
			addUndoCommand(this); // Store this command
			clearRedoList(); // Clear any stored redo commands
			System.out.println("Done.");
		} else {
			Member DupicateMemberObj = Club.getInstance().findMember(memberId);//throw an error if the member already exist
			throw new ExDuplicateMemberID(DupicateMemberObj);
		}
	}


	//undo
	@Override
	public void undoMe() {
		Club.getInstance().removeMember(member);
		addRedoCommand(this); // <====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}

	//redo
	@Override
	public void redoMe() {
		Club.getInstance().addMember(member);
		addUndoCommand(this); // <====== upon redo, we should keep a copy in the undo list
	}
}