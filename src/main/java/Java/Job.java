package Java;

import java.util.Arrays;

public class Job {

    private Role[] roles;
    private int productionID;
    private String[] characterNames;

    public Job(Role[] roles, int productionID, String[] characterNames) {
        this.roles = roles;
        this.productionID = productionID;
        this.characterNames = characterNames;
    }

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    public int getProgram() {
        return productionID;
    }

    public void setProgram(int program) {
        this.productionID = productionID;
    }

    public String[] getCharacterNames() {
        return characterNames;
    }

    public void setCharacterNames(String[] characterNames) {
        this.characterNames = characterNames;
    }

    @Override
    public String toString() {
        return "Production ID: " + productionID + ", Roles on production: " + Arrays.toString(roles) +
                (characterNames != null ? " as " + Arrays.toString(characterNames) : "");
    }
}
