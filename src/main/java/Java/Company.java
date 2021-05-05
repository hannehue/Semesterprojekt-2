package Java;

import java.util.Date;

public class Company extends Credit implements ICompany{

    private int companyID;

    public Company(String name, Date dateAdded, int creditID, boolean approved, String description, int companyID){
        super(name, dateAdded, creditID, approved, description);
        this.companyID = companyID;
    }

    @Override
    public int getCompanyID() {
        return companyID;
    }

    @Override
    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }


    @Override
    public String toFileString() {
        return null;
    }
}
