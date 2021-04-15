package Java;

public class Job {

    private Role[] roles;
    private Production program;
    private String[] characterNames;

    public Job(Role[] roles, Production program, String[] characterNames) {
        this.roles = roles;
        this.program = program;
        this.characterNames = characterNames;
    }

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

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    public Production getProgram() {
        return program;
    }

    public void setProgram(Production program) {
        this.program = program;
    }

    public String[] getCharacterNames() {
        return characterNames;
    }

    public void setCharacterNames(String[] characterNames) {
        this.characterNames = characterNames;
    }
}
