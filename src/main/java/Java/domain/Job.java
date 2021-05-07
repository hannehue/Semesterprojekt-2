package Java.domain;

import Java.domain.ApplicationManager;
import Java.interfaces.IJob;

public class Job implements IJob {

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

    public Job(int personId, Role roles) {
        this.role = roles;
        this.personId = personId;
    }

    public Job(Role role, int productionID) {
        this.role = role;
        this.productionID = productionID;
    }

    @Override
    public String toString(){
        try {
            if (characterName != null) {
                return role.toString() + " som " + characterName;
            }
            return role.toString();
        }
        catch (NullPointerException e) {
            return "asdfasdfasl;dfkj";
        }
    }

    @Override
    public int getProgram() {
        return productionID;
    }

    @Override
    public void setProgram(int program) {
        this.productionID = productionID;
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public void setJobs() {

    }

    @Override
    public String getCharacterName() {
        return characterName;
    }

    @Override
    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    @Override
    public int getPersonId() {
        return personId;
    }
}
