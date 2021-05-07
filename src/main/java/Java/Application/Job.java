package Java.Application;

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
                return role.toString() + " som " + characterName + " i " + CreditSystemController.getInstance().getProduction(productionID).getName();
            }
            return role.toString() + " i " + CreditSystemController.getInstance().getProduction(productionID).getName();
        }
        catch (NullPointerException e) {
            return "";
        }
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
