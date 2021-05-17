package Java.domain;

import Java.data.DatabaseLoader;
import Java.domain.data.*;
import Java.domain.services.*;
import Java.interfaces.*;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.IOException;

public class ApplicationManager {

    private DatabaseLoader dataLoader;
    private UserType userType;
    private String searchFieldPlaceholder = "";

    private static final ApplicationManager instance = new ApplicationManager();
    private int idTracker = 0; //should be moved to database (tracker id for Movie og Person)

    private ApplicationManager() {

        /*try{
            dataLoader = DatabaseLoader.getInstance();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        /*
        for (String[] strings: dataLoader.getPersonArraylist()){
            personList.add(dataLoader.queryToPerson(strings));
        }
        for (String[] strings: dataLoader.getMovieArrayList()){
            movieList.add(dataLoader.queryToMovie(strings));
        }

        System.out.println(personList.toString());*/
    }

    public static ApplicationManager getInstance() {
        return instance;
    }

    public int nextId() {
        int temp = idTracker;
        idTracker++;
        return temp;
    }



    public void onStop(){

        //dataLoader.addCreditsToDatabase((ArrayList<IPerson>) personList);
        //dataLoader.addCreditsToDatabase((ArrayList<IMovie>) movieList);
        //dataLoader.addCreditsToDatabase((ArrayList<IShow>) showMap);

        try {
            dataLoader.writeAllCredits();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userTypeSetter) {
        userType = userTypeSetter;
    }


    public String getSearchFieldPlaceholder() {
        return searchFieldPlaceholder;
    }

    public void setSearchFieldPlaceholder(String searchFieldPlaceholder) {
        ApplicationManager.getInstance().searchFieldPlaceholder = searchFieldPlaceholder;
    }

    /**
     * Aprrove some credit with an id, from a list.
     * @param id
     * @param observableList
     * @param <T>
     */
    public  <T extends ICredit> void approveCredit(int id, ObservableList<T> observableList) {
        T approveCredit = null;
        for (T credit: observableList) {
            if (credit.getCreditID() == id) {
                approveCredit = credit;
            }
        }
        if (approveCredit != null){
            observableList.remove(approveCredit);
            approveCredit.setApproved(true);
            observableList.add(approveCredit);
        }
    }


    public <T extends ICredit> void approveCredit(int id, ObservableMap<Integer,T> map) {
        T credit = null;
        if (map.containsKey(id)) {
            credit = map.remove(id);
            credit.setApproved(true);
        }
        if (credit != null) {
            map.put(id, credit);
        }
    }
}