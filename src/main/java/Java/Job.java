package Java;

public class Job {

    private Role role;
    private int productionId;
    private String characterName;
    private int personId;

    public Job(Role role, String characterName, int productionId) {
        this.role = role;
        this.productionId = productionId;
        this.characterName = characterName;
    }

    public Job(int personId, Role role, String characterName) {
        this.role = role;
        this.personId = personId;
        this.characterName = characterName;
    }

    public Job(Role role, int productionId) {
        this.role = role;
        this.productionId = productionId;

    }

    public Job(int personId, Role roles) {
        this.role = roles;
        this.personId = personId;
    }

    @Override
    public String toString(){
        if (characterName != null) {
           return role.toString() + " som " + characterName + " i " + CreditSystemController.getProduction(productionId).getName();
        }
        return role.toString() + " i " + CreditSystemController.getProduction(productionId).getName();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getCharacterNames() {
        return characterName;
    }

    public void setCharacterNames(String characterName) {
        this.characterName = characterName;
    }

    public int getPersonId() {
        return personId;
    }

}
