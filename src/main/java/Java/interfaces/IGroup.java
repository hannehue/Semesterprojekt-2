package Java.interfaces;

public interface IGroup extends ICredit{

    int getGroupID();
    void setGroupID(int groupID);

    String toFileString();
}
