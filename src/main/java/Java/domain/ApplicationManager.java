package Java.domain;

import Java.data.DatabaseLoaderFacade;
import Java.domain.data.*;
import Java.domain.services.*;
import Java.interfaces.*;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class ApplicationManager {

    private UserType userType;

    private static final ApplicationManager instance = new ApplicationManager();

    private ApplicationManager() {
    }

    public static ApplicationManager getInstance() {
        return instance;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userTypeSetter) {
        userType = userTypeSetter;
    }

    /**
     * Aprrove some credit with an id, from a list.
     * @param id
     * @param observableList
     * @param <T>
     */
    public <T extends ICredit> void approveCredit(int id, ObservableList<T> observableList) {
        T approveCredit = null;
        for (T credit: observableList) {
            if (credit.getCreditID() == id) {
                approveCredit = credit;
            }
        }
        if (approveCredit != null){
            observableList.remove(approveCredit);
            approveCredit.setApproved(true);
            try {
              DatabaseLoaderFacade.getInstance().setCreditApproveState((ICredit) approveCredit, true);
            }
            catch (SQLException e){
              e.printStackTrace();
            }
            observableList.add(approveCredit);
        }
    }


    public ArrayList<ICredit> search(String searchString){
        ArrayList<ICredit> creditsList = new ArrayList<ICredit>();
        creditsList.addAll(PersonManager.getInstance().searchPerson(searchString));
        creditsList.addAll(MovieManager.getInstance().searchMovie(searchString));
        creditsList.addAll(ShowManager.getInstance().searchShows(searchString));
        return creditsList;
    }

    public void searchShow( String search, ObservableList<IShow> list){
        list.addAll(DatabaseLoaderFacade.getInstance().searchShowsFromDatabase(search));
        //list.add(new Show("name", "desc", false));
    }

}
