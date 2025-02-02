public class CmdEquipmentBorrow extends RecordedCommand {
    String Equipmentid;
    String MemberId;
    EquipmentSet borrowedSet;
    int borrowdays;
    Member member;

    @Override
    public void execute(String[] cmdParts)
            throws ExMemberNotFound, ExEquipmentNotFound, ExEquipmentSetNotAvailable,
            ExNegativeRequestDays, ExOverlappingRequest, ExDoubleBorrow, ExOverlappingRequestAndBorrow {
        MemberId = cmdParts[1];
        Equipmentid = cmdParts[2];
        if (cmdParts.length > 3) {
            borrowdays = Integer.parseInt(cmdParts[3]); //throw error if negative days
            if (borrowdays <= 0) {
                throw new ExNegativeRequestDays();
            }
        } else {
            borrowdays = 7;
        }

        member = Club.getInstance().findMember(MemberId);
        Equipment equipmentObj = Club.getInstance().findequipment(Equipmentid);

        if (member == null) {
            throw new ExMemberNotFound();//throw error if member not found
        } else if (equipmentObj == null) {
            throw new ExEquipmentNotFound(); //throw error if equipment not found
        }
        for (EquipmentSet set : equipmentObj.getSets()) {
            if (set.getborrower() != null && set.getborrower().equals(member.getid())) {
                throw new ExDoubleBorrow(); //throw error if the member has already borrowed 
            }
        }

        borrowedSet = null;
        for (EquipmentSet setObject : equipmentObj.getSets()) {
            if (setObject.isAvailable()) {//if the set is available then
                Day currentStart = SystemDate.getInstance();
                Day currentEnd = currentStart.AddDays(borrowdays);
                for (Request requestObj : setObject.getallrequestlist()) {//check with all the already requests ojects
                    if (member.getid().equals(requestObj.getmemberid()) && (requestObj.HasConflict(currentEnd, currentEnd))) {
                        {
                            throw new ExOverlappingRequest();//throw an error there is overlap with the request object
                        }
                    }
                }
                if (!setObject.hasOverlap(currentStart, currentEnd)) {//if no overlap
                    borrowedSet = setObject; // set the borrowed object
                    borrowedSet.borrow(member.getid(), borrowdays);
                    System.out.println(member.getid() + " " + member.getName() +
                            " borrows " + borrowedSet.getSetCode() + " (" + equipmentObj.getName() + ") for " +
                            borrowedSet.getstartDay().toString() + " to " +
                            borrowedSet.getEndDay().toString());
                    System.out.println("Done.");
                    break;
                }
            }
        }
        if (borrowedSet == null) {
            throw new ExEquipmentSetNotAvailable();//throw an error if the set is still not available 
        }
        addUndoCommand(this);
        clearRedoList();

    }

    //undo
    @Override
    public void undoMe() {
        if (borrowedSet != null) {
            borrowedSet.returnEquipment();
            member.decrementborrowedequipments();
            addRedoCommand(this);
        }
    }


    //redo
    @Override
    public void redoMe() {
        if (borrowedSet != null) {
            member = Club.getInstance().findMember(MemberId);
            if (member != null) {
                member.incrementborrowedequipments();
                try {
                    borrowedSet.borrow(member.getid(), borrowdays);
                } catch (ExOverlappingRequestAndBorrow e) {
                    e.getMessage();
                }
                addUndoCommand(this);
            }
        }
    }
}
