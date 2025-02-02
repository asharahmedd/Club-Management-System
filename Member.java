import java.util.ArrayList;

public class Member implements Comparable<Member> {

    private String Memberid;
    private String Membername;
    private Day joinDate;
    private int borrowedqequipments = 0;
    private int Requestedequipments = 0;

    public Member(String id, String name) {
        this.Memberid = id;
        this.Membername = name;
        this.joinDate = SystemDate.getInstance().clone();
    }

    // listing the members
    public static void list(ArrayList<Member> allMembers) {
        System.out.printf("%-5s%-9s%11s%11s%13s\n", "ID", "Name", "Join Date ", "#Borrowed", "#Requested");
        for (Member m : allMembers) {
            System.out.printf("%-5s%-9s%11s%7d%13d\n", m.Memberid, m.Membername, m.joinDate.toString(),
                    m.borrowedqequipments,
                    m.Requestedequipments);
        }
    }

    // incrementing the borrowed equipments
    public void incrementborrowedequipments() {
        borrowedqequipments++;
    }

    // decrementing the borrowed equipments
    public void decrementborrowedequipments() {
        borrowedqequipments--;
    }

    // incrementing the requested equipments
    public void incrementrequestedequipments() {
        Requestedequipments++;
    }

    // decrement the requested equipments
    public void decrementrequestedequipments() {
        Requestedequipments--;
    }

    // compare function used in sorting
    @Override
    public int compareTo(Member otherMember) {
        return this.Memberid.compareTo(otherMember.getid());
    }

    // getters
    public String getid() {
        return Memberid;
    }

    public String getName() {
        return Membername;
    }

    public Day getDateString() {

        return joinDate;
    }

    public int getborrowed() {
        return borrowedqequipments;
    }

    // list all status
    public static void listAllStatus(ArrayList<Member> list) {
        for (Member m : list) {
            System.out.println("[" + m.getid() + " " + m.getName() + "]");
            if (m.getborrowed() == 0 && m.Requestedequipments == 0) {
                System.out.println("No record.");
            } else {

                for (Equipment e : Club.getInstance().getEquipment()) {
                    for (EquipmentSet set : e.getSets()) {
                        if (set.getborrower() != null && set.getborrower().equals(m.getid())) {
                            System.out.println("- borrows " + set.getSetCode() +
                                    " (" + e.getName() + ") for " +
                                    set.getstartDay() + " to " +
                                    set.getEndDay());
                        }
                    }
                }
                for (Equipment e : Club.getInstance().getEquipment()) {
                    for (EquipmentSet set : e.getSets()) {
                        for (Request request : set.getallrequestlist()) {
                            if (request.getmemberid().equals(m.getid())) {
                                System.out.println("- requests " + set.getSetCode() +
                                        " (" + e.getName() + ") for " +
                                        request.toString());
                            }
                        }
                    }
                }
            }
            System.out.println("");
        }
    }
}
