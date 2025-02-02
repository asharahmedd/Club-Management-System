public class Request implements Comparable<Request> {
    private Day startDay;
    private Day endDay;
    private Member member;

    public String getmemberid() {
        return member.getid();
    }
    //check if there is a conflict in days
    public Boolean HasConflict(Day s, Day e) {
        return (s.compareTo(this.endDay) <= 0) && (this.startDay.compareTo(e) <= 0);
    }
    //constructors
    public Request(Day startDayp, Day endDayp, Member memberp) {
        this.startDay = startDayp;
        this.endDay = endDayp;
        this.member = memberp;
    }

    public Request() {
        this.startDay = SystemDate.getInstance().clone();
        this.endDay = SystemDate.getInstance().clone();
        this.member = null;
    }

    //setting the object (current borrowed/request)
    public void setObject(Day startDayp, Day endDayp, Member memberp) {
        this.startDay = startDayp;
        this.endDay = endDayp;
        this.member = memberp;
    }


    //checking if the object is equals or not
    public boolean isequals(Day startDayp, Day endDayp, Member memberp) {
        return (this.startDay.toString().equals(startDayp.toString())
                && this.endDay.toString().equals(endDayp.toString())
                && this.member.getid().equals(memberp.getid()));
    }

    public Day getEndDay() {
        return endDay;
    }

    public Day getStartDay() {
        return startDay;
    }

    public Member getMember() {
        return member;
    }

    public String toString() {
        return startDay.toString() + " to " + endDay.toString();
    }

    //comparing used in sorting 
    @Override
    public int compareTo(Request otherRequest) {
        int x = startDay.compareTo(otherRequest.startDay);
        if (x == 0) {
            x = endDay.compareTo(otherRequest.endDay);
        }
        return x;
    }

}