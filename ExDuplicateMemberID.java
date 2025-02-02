public class ExDuplicateMemberID extends Exception{
        public ExDuplicateMemberID(String message) {
            super(message);
    
    }
    public ExDuplicateMemberID(Member member) {
        super("Member ID already in use: " + member.getid() + " "+ member.getName());

}
}
