package Java.domain.data;

import Java.interfaces.IJob;

public class Job implements IJob {

    private Role role;
    private int productionID;
    private String characterName;
    private int personId;

    public Job(int personId, Role role, String characterName, int productionID) {
        this.role = role;
        this.productionID = productionID;
        this.characterName = characterName;
        this.personId = personId;
    }

    public Job(int personId, Role role, int productionID) {
        this.role = role;
        this.productionID = productionID;
        this.personId = personId;
    }

    @Override
    public String toString(){
        try {
            if (characterName != null) {
                return role.toString() + " som " + characterName;
            }
            return role.toString() + " | " + productionID;
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
