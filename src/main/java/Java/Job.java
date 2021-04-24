package Java;

public class Job {

    private Role[] roles;
    private int programId;
    private String[] characterNames;

    public Job(Role[] roles, String[] characterNames, int programId) {
        this.roles = roles;
        this.programId = programId;
        this.characterNames = characterNames;
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

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    public String[] getCharacterNames() {
        return characterNames;
    }

    public void setCharacterNames(String[] characterNames) {
        this.characterNames = characterNames;
    }
}
