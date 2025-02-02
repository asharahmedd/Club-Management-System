import java.util.*;

public class Equipment implements Comparable<Equipment>{
    public ArrayList<EquipmentSet> Sets;
    public String id;
    public String name;
    private int setCounter = 0;

    public Equipment(String idp, String namep) {
        id = idp;
        name = namep;
        Sets = new ArrayList<>();
    }
    //getting the code 
    public String getCode() {
        return id;
    }
    //getting the name
    public String getName() {
        return name;
    }
    //getting all the sets 
    public ArrayList<EquipmentSet> getSets() {
        return Sets; 
    }
    //adding a set
    public void addSet() {
        Sets.add(new EquipmentSet(this.getCode(), Sets.size() + 1));
        Collections.sort(Sets);//sorting
    }
    //getting the count of set
    public int getSetCount() {
        return Sets.size(); 
    }
    //increment the number of sets
    public void incrementSets(String id) {
        setCounter++; 
        Sets.add(new EquipmentSet(id, setCounter)); 
    }
    //decrement the number of sets
    public void decrementSets(String id) {
        if (!Sets.isEmpty()) {
            Sets.remove(Sets.size() - 1); 
            setCounter--; 
        }
    }

    //listing the borrow info
    public String listborrowinfo(Equipment e) {
        String output = "";
        for (EquipmentSet m : e.getSets()) {
            if (m.getborrower() != null && !m.getborrower().isEmpty()) {
                if (!output.equals("")) {
                    output = output + ", ";
                }
                output = output + m.getSetCode() + "(" + m.getborrower() + ")";
            }
        }
        return output.equals("") ? "" : " (Borrowed set(s): " + output + ")";
    }

    //listing the general info
    public static void list(ArrayList<Equipment> allEquipments) {
        System.out.printf("%-3s %-18s %3s %s\n", "Code", "Name", "#Sets", "");
        for (Equipment e : allEquipments) {
            String borrowInfo = e.listborrowinfo(e);
            System.out.printf("%-3s %-18s %-3d%s\n",
                    e.getCode(),
                    e.getName(),
                    e.getSetCount(),
                    borrowInfo);
        }
    }


    //listing all the status
    public static void listAllStatus(ArrayList<Equipment> allEquipments) {
        for (Equipment e : allEquipments) {
            System.out.println("[" + e.getCode() + " " + e.getName() + "]");
            if (e.getSets().isEmpty()) {
                System.out.println("  We do not have any sets for this equipment.");
            } else {
                for (EquipmentSet set : e.getSets()) {
                    System.out.println("  " + set.getSetCode());
                    System.out.println(getSetStatus(set));
                    if (set.getrequeststatuString() != "") {
                        System.out.printf("    Requested period(s): %s\n", set.getrequeststatuString());
                    }
                }
            }
            System.out.println("");
        }
    }


    //getting the status of the set and ouputting it 
    private static String getSetStatus(EquipmentSet set) {
        String status = "";
        if (set.isAvailable()) {
            status = "    Current status: Available";
        } else {
            Member borrower = Club.getInstance().findMember(set.getborrower());
            status = String.format("    Current status: %s %s borrows for %s to %s",
                    borrower.getid(),
                    borrower.getName(),
                    set.getstartDay().toString(),
                    set.getEndDay().toString());
       }

        return status;
    }


    //requesting an equipment (calls the request function of the equipment set)
    public void requestingquipment(Member Member, int requestDays, Day startDay, Day endDay, Boolean flagtoredo)
            throws ExOverlappingRequestAndBorrow, ExSetIsNotAvailable, ExOverlappingRequest {
        for (EquipmentSet equipmentSet : Sets) {
            Boolean requestsuccess = equipmentSet.requestingSet(startDay, endDay, Member, this.name, flagtoredo);
            {
                if (requestsuccess) {
                    return;
                }
            }
        }
        throw new ExSetIsNotAvailable();//throw error if the requesting fails
    }


    //used in undor i.e unrequesting an equipment (calls the unrequest function of the equipment set)
    public void unrequestingEquipment(Member member, int requestDays, String StartDay) throws ExInvalidDateFormat {
        for (EquipmentSet equipmenteSet : Sets) {
            if (equipmenteSet.unRequestingSet(member, requestDays, StartDay)) {
                break;
            }
        }
    }

    //compare i.e used in sorting
    @Override
    public int compareTo(Equipment otherEquipment) {
        return this.getCode().compareTo(otherEquipment.getCode());
    }

}