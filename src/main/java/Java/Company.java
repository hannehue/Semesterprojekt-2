package Java;

import java.util.Date;

public class Company extends Credit{

    private int companyID;

    public Company(String name, Date dateAdded, int creditID, boolean approved, String description, int companyID){
        super(name, dateAdded, creditID, approved, description);
        this.companyID = companyID;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }
}
