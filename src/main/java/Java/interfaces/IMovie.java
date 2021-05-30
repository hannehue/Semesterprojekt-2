package Java.interfaces;

import Java.domain.data.Category;

public interface IMovie extends IProduction{

    void setLengthInSecs(int LengthInSecs);

    void addStaffID(int id);
}
