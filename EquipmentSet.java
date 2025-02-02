import java.util.ArrayList;
import java.util.Collections;

public class EquipmentSet implements Comparable<EquipmentSet> {
    private String code;
    private Boolean status;
    private String borrower;
    private int borrowdays;
    private Day startDay;
    private Day endDay;
    private Request CurrentBorrowedSet;
    private ArrayList<Request> allrequestList;

    //constructor
    public EquipmentSet(String equipmentCode, int setNumber) {
        this.code = equipmentCode + "_" + setNumber;
        this.status = true;
        this.CurrentBorrowedSet = new Request();
        this.allrequestList = new ArrayList<Request>();
    }
    //getting all the requeuested objects
    public ArrayList<Request> getallrequestlist() {
        return allrequestList;
    }

    //compare function used in sorting
    @Override
    public int compareTo(EquipmentSet otherEquipmentSet) {
        return this.getSetCode().compareTo(otherEquipmentSet.getSetCode());
    }


    //getting the request period of the equipment set
    public String getrequeststatuString() {
        String output = "";
        for (int i = 0; i < allrequestList.size(); i++) {
            output += allrequestList.get(i).toString();
            if (i != allrequestList.size() - 1)
            output += ", ";
        }
        return output;
    }
    //getting the set code
    public String getSetCode() {
        return code;
    }
    //checking if the set is available or not
    public boolean isAvailable() {
        return status;
    }

    //checking if there is an overlap or not
    public boolean hasOverlap(Day borrowStart, Day borrowEnd) {
        for (Request requestobject : allrequestList) {
            Day requestStart = requestobject.getStartDay(); 
            Day requestEnd = requestobject.getEndDay(); 
            if (Day.dateoverlaps(borrowStart, borrowEnd, requestStart, requestEnd)) {
                return true; 
            }
        }
        return false;
    }

    //function to borrow the particular object
    public void borrow(String id, int n) throws ExOverlappingRequestAndBorrow {
        Member member = Club.getInstance().findMember(id);
        if (status) {
            status = false;
            borrower = id;
            if (n <= 0) {
                borrowdays = 7;
            } else {
                borrowdays = n;
            }
            startDay = SystemDate.getInstance().clone();
            member.incrementborrowedequipments();
            endDay = startDay.clone();
            endDay = endDay.AddDays(borrowdays);
            this.CurrentBorrowedSet.setObject(startDay, endDay, member);
        }
    }


    //return the equipment if borrowed
    public void returnEquipment() {
        if (status == false) {
            status = true;
            borrower = null;
            startDay = null;
            endDay = null;
            borrowdays = 0;
        }
    }

    //all getters
    public String getborrower() {
        return borrower;
    }

    public int getborrowdays() {
        return borrowdays;
    }

    public void setborrowdays(int n) {
        borrowdays = n;
    }

    public Day getstartDay() {
        return startDay;
    }

    public Day getEndDay() {
        return endDay;
    }


    //request function to get the equipment set
    public Boolean requestingSet(Day startDay, Day endDay, Member member, String Name, Boolean flagtoRedo)
            throws ExOverlappingRequest, ExOverlappingRequestAndBorrow, ExSetIsNotAvailable {

        String MemberId = member.getid();
        boolean OverlappingDetected = false;

        if (CurrentBorrowedSet.HasConflict(startDay, endDay)) {
            if ((CurrentBorrowedSet.getMember().equals(member)) && (CurrentBorrowedSet.getmemberid().equals(MemberId))) {
                throw new ExOverlappingRequestAndBorrow();//if there is an overlapping between borrow and request
            }
            OverlappingDetected = true;
        }
        for (Request requestobject : allrequestList) {//checking with current requested sets
            if (requestobject.getmemberid().equals(MemberId)) {
                if (requestobject.HasConflict(startDay, endDay)) {
                    throw new ExOverlappingRequestAndBorrow();//if there is an overlapping
                }
            } else {
                OverlappingDetected = requestobject.HasConflict(startDay, endDay);
                if (OverlappingDetected) {
                    break;
                }
            }
        }

        if (!OverlappingDetected) {//if there is no overlapping simple print
            Request newRequest = new Request(startDay, endDay, member);
            allrequestList.add(newRequest);
            Collections.sort(allrequestList);
            if (!flagtoRedo) {
                System.out.println(MemberId + " " + member.getName() + " requests " + code +
                        " (" + Name + ") for " + newRequest.toString());
            }
            return true;
        }
        return false;
    }


    //function to unrequest a set used in undo
    public Boolean unRequestingSet(Member m, int requestDays, String sDay) throws ExInvalidDateFormat {
        Day startDay = new Day(sDay);
        Day endDay = startDay.AddDays(requestDays);
        for (int i = allrequestList.size() - 1; i >= 0; i--) {
            Request request = allrequestList.get(i);
            if (request.isequals(startDay, endDay, m)) {//finding the equipment set that matches
                allrequestList.remove(i);
                return true;
            }
        }
        return false;
    }
}