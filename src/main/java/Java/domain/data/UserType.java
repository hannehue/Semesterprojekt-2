package Java.domain.data;

public enum UserType {
    ADMIN("Admin", true, true, true, false, true),
    MODERATOR("Moderator", false, true, true, false, true),
    PRODUCER("Producer", false, true, false, true, false),
    PERSON("Person", false, false, false, true, false);

    private final String userType;
    private final boolean addUser;
    private final boolean addCredit;
    private final boolean approveCredit;
    private final boolean personalProfile;
    private final boolean creditOverlook;

    UserType(String userType, Boolean addUser, Boolean addCredit, Boolean approveCredit, Boolean personalProfile, Boolean creditOverlook){
        this.userType = userType;
        this.addUser = addUser;
        this.addCredit = addCredit;
        this.approveCredit = approveCredit;
        this.personalProfile = personalProfile;
        this.creditOverlook = creditOverlook;
    }

    public String toString() {
        return userType;
    }

    public boolean getAddUser() {
        return addUser;
    }

    public boolean getAddCredit() {
        return addCredit;
    }

    public boolean getApproveCredit() {
        return approveCredit;
    }

    public boolean getPersonalProfile() {return personalProfile;}

    public boolean getCreditOverlook(){return creditOverlook;}
}
