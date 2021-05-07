package Java.Application;

public interface IGroup extends ICredit{

    int getGroupID();
    void setGroupID(int groupID);

    String toFileString();
}
