package Java;

import java.util.Arrays;

public class Job {

    private Role role;
    private int productionID;
    private String characterName;
    private int personId;

    public Job(Role role, String characterName, int productionID) {
        this.role = role;
        this.productionID = productionID;
        this.characterName = characterName;
    }

    public Job(int personId, Role role, String characterName) {
        this.role = role;
        this.personId = personId;
        this.characterName = characterName;
    }

    public Job(Role role, int productionID) {
        this.role = role;
        this.productionID = productionID;
    }

    @Override
    public String toString(){
        if (characterName != null) {
           return role.toString() + " som " + characterName + " i " + CreditSystemController.getProduction(productionID).getName();
        }
        return role.toString() + " i " + CreditSystemController.getProduction(productionID).getName();
    }

    public int getProgram() {
        return productionID;
    }

    public void setProgram(int program) {
        this.productionID = productionID;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setJobs() {

    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public int getPersonId() {
        return personId;
    }
}
