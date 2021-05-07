package Java.interfaces;

import Java.domain.Role;

public interface IJob {

    int getProgram();
    void setProgram(int program);

    Role getRole();
    void setRole(Role role);

    void setJobs();

    String getCharacterName();
    void setCharacterName(String characterName);

    int getPersonId();


}
