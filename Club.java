import java.util.ArrayList;
import java.util.Collections;

public class Club {

    private ArrayList<Member> allMembers;
    private ArrayList<Equipment> allEquipments;
    private static Club instance = new Club();

    private Club() {
        allMembers = new ArrayList<Member>();
        allEquipments = new ArrayList<Equipment>();
    }

    public static Club getInstance() {
        return instance;
    }

    // add a member
    public void addMember(Member m) {
        allMembers.add(m);
        Collections.sort(allMembers);
    }

    // remove a member
    public void removeMember(Member m) {
        allMembers.remove(m);
    }

    // list all members
    public void listClubMembers() {
        Member.list(this.allMembers);
    }

    // check if a members exist or not
    public Member checkmember(String m) {
        for (Member member : allMembers) {
            if (member.getid().equals(m)) {
                return member;
            }
        }
        return null;
    }

    // find the member
    public Member findMember(String id) {
        for (Member e : allMembers) {
            if (e.getid().equals(id)) {
                return e;
            }
        }
        return null;
    }

    // list member status
    public void listMemberStatus() {
        Member.listAllStatus(this.allMembers);
    }

    // list all equipments
    public void listClubEquipment() {
        Equipment.list(this.allEquipments);
    }

    // create equipment
    public void createequipment(Equipment e) {
        allEquipments.add(e);
    }

    // add an equipment
    public void addequipment(String id) {
        for (Equipment e : allEquipments) {
            if (id.equals(e.getCode())) {
                e.incrementSets(id);
            }
        }
        Collections.sort(allEquipments);
    }

    // decrement the equipment set using id
    public void removeequipment(String id) {
        for (Equipment e : allEquipments) {
            if (id.equals(e.getCode())) {
                e.decrementSets(id);
            }
        }

    }

    // remove an equipment
    public void removeequipment(Equipment e) {
        allEquipments.remove(e);
    }

    // check an equipment
    public Equipment checkequipment(String e) {
        for (Equipment equipment : allEquipments) {
            if (equipment.getCode().equals(e)) {
                return equipment;
            }
        }
        return null;
    }

    // check if an equipment exist or not
    public boolean checkexistingequipment(String e) {
        for (Equipment equipment : allEquipments) {
            if (equipment.getCode().equals(e)) {
                return true;
            }
        }
        return false;

    }

    // find the equipment
    public Equipment findequipment(String id) {
        for (Equipment m : allEquipments) {
            if (m.getCode().equals(id)) {
                return m;
            }
        }
        return null;
    }

    // list equipment status
    public void listClubEquipmentStatus() {
        Equipment.listAllStatus(this.allEquipments);
    }

    // return all equipments
    public ArrayList<Equipment> getEquipment() {
        return allEquipments;
    }

}
