
public class CmdEquipmentRequest extends RecordedCommand {
    String Equipmentid;
    String MemberId;
    EquipmentSet requestedSet;
    Member member;
    Day startDay;
    int requestDays;

    @Override
    public void execute(String[] cmdParts) throws ExMemberNotFound, ExEquipmentNotFound,
            ExEquipmentSetNotAvailable, ExInvalidDateFormat, ExInavlidRequestDays, ExNegativeRequestDays,
            ExOverlappingRequest, ExOverlappingRequestAndBorrow, ExSetIsNotAvailable {

        MemberId = cmdParts[1];
        Equipmentid = cmdParts[2];
        startDay = Day.fromString(cmdParts[3]);

        try {
            requestDays = Integer.parseInt(cmdParts[4]);
        } catch (NumberFormatException e) {
            throw new ExInavlidRequestDays();//throw an error invalid request days
        }
        if (requestDays <= 0) {
            throw new ExNegativeRequestDays();//throw an error if negative request days
        }

        Day endDay = startDay.AddDays(requestDays);
        member = Club.getInstance().findMember(MemberId);
        Equipment equipment = Club.getInstance().findequipment(Equipmentid);
        if (member == null) {
            throw new ExMemberNotFound();//throw error if member not found
        } else if (equipment == null) {
            throw new ExEquipmentNotFound();//throw error if equipment not found
        }

        equipment.requestingquipment(member, requestDays, startDay, endDay, false);//
        member.incrementrequestedequipments();//increment the requested sets
        addUndoCommand(this);
        clearRedoList();
        System.out.println("Done.");
    }


    //undo
    @Override
    public void undoMe() {
        try {
            member = Club.getInstance().findMember(MemberId);
            Equipment equipment = Club.getInstance().findequipment(Equipmentid);
            equipment.unrequestingEquipment(member, requestDays, startDay.toString());
            member.decrementrequestedequipments();
            addRedoCommand(this);
        } catch (ExInvalidDateFormat e) {
            System.out.println(e.getMessage());
        }

    }


    //redo
    @Override
    public void redoMe() {
        try {
            member = Club.getInstance().findMember(MemberId);
            Equipment equipment = Club.getInstance().findequipment(Equipmentid);
            Day endDay = startDay.AddDays(requestDays);
            member.incrementrequestedequipments();
            equipment.requestingquipment(member, requestDays, startDay, endDay, true);
        }
        catch (ExOverlappingRequestAndBorrow e) {
            System.out.println(e.getMessage());

        } catch (ExSetIsNotAvailable e) {
            System.out.println(e.getMessage());

        } catch (ExOverlappingRequest e) {
            System.out.println(e.getMessage());

        }
        addUndoCommand(this);
    }
}