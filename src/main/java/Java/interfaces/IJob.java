package Java.interfaces;

import Java.domain.data.Role;

public interface IJob {

    int getProgram();
    void setProgram(int program);

    Role getRole();
    void setRole(Role role);


    String getCharacterName();
    void setCharacterName(String characterName);

    int getPersonId();


}
