package Java;

public enum UserType {
    ADMIN("Admin", true, true, true),
    MODERATOR("Moderator", false, true, true),
    PRODUCER("Producer", false, true, false),
    PERSON("Person", false, false, false);

    private final String userType;
    private final boolean addUser;
    private final boolean addCredit;
    private final boolean approveCredit;

    UserType(String userType, Boolean addUser, Boolean addCredit, Boolean approveCredit){
        this.userType = userType;
        this.addUser = addUser;
        this.addCredit = addCredit;
        this.approveCredit = approveCredit;
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
}
