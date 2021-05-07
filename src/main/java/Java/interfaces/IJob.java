package Java.interfaces;

import Java.domain.Role;

import java.util.ArrayList;

public interface IJob {

    int getProgram();
    void setProgram(int program);

    Role getRole();
    void setRole(Role role);


    String getCharacterName();
    void setCharacterName(String characterName);

    int getPersonId();


}
