package Java.interfaces;

public interface ICompany extends ICredit {

    int getCompanyID();
    void setCompanyID(int companyID);

    String toFileString();
}
