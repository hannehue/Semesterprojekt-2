package Java.Presentation;

public enum UserType {
    ADMIN("Admin", true, true, true, false),
    MODERATOR("Moderator", false, true, true, false),
    PRODUCER("Producer", false, true, false, true),
    PERSON("Person", false, false, false, true);

    private final String userType;
    private final boolean addUser;
    private final boolean addCredit;
    private final boolean approveCredit;
    private final boolean personalProfile;

    UserType(String userType, Boolean addUser, Boolean addCredit, Boolean approveCredit, Boolean personalProfile){
        this.userType = userType;
        this.addUser = addUser;
        this.addCredit = addCredit;
        this.approveCredit = approveCredit;
        this.personalProfile = personalProfile;
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
}
