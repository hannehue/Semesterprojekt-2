package Java.interfaces;

import Java.domain.data.Category;

import java.util.ArrayList;
import java.util.Date;

public interface IProduction extends ICredit {

    int getProductionID();
    void setProductionID(int id);
    int getLengthInSecs();

    Category[] getCategories();
    void setCategories(Category[] categories);

    Date getReleaseDate();

    ArrayList<Integer> getStaffIDs();
}
