package Java;

public class Job {

    private Role role;
    private int programId;
    private String characterNames;
    private int personId;

    public Job(Role role, String characterNames, int programId) {
        this.role = role;
        this.programId = programId;
        this.characterNames = characterNames;
    }

    public Job(int personId, Role roles, String characterNames) {
        this.role = roles;
        this.personId = personId;
        this.characterNames = characterNames;
    }

    public Job(Role role, int programId) {
        this.role = role;
        this.programId = programId;

    }

    public Job(int personId, Role roles) {
        this.role = roles;
        this.personId = personId;
    }

    /*
    @Override
    public String toString(){
        String allRoles = "Roles: ";
        for (Role role : roles){
            allRoles += role.toString() + ", ";
        }
        allRoles.substring(0, allRoles.length() - 3);
        allRoles += " Played: ";
        for (String str : characterNames) {
            allRoles += str + ", ";
        }
        allRoles.substring(0, allRoles.length() - 3);
        return program.toString() + ": " + allRoles;
    }

     */

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getCharacterNames() {
        return characterNames;
    }

    public void setCharacterNames(String characterNames) {
        this.characterNames = characterNames;
    }

    public int getPersonId() {
        return personId;
    }

}
