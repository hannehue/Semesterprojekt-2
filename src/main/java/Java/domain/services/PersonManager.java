package Java.domain.services;

import Java.data.DatabaseLoader;
import Java.domain.ApplicationManager;
import Java.domain.data.Person;
import Java.interfaces.ICredit;
import Java.interfaces.IPerson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;

public class PersonManager {
    private static PersonManager instance = new PersonManager();
    private PersonManager(){}

    public static PersonManager getInstance() {
        return instance;
    }

    private final ObservableList<IPerson> personList = FXCollections.observableArrayList();

    public void addPerson(String name, String description, String phoneNumber, String email) {
        IPerson person = new Person(
                name,
                new Date(),
                ApplicationManager.getInstance().nextId(), // change later
                false,
                description,
                ApplicationManager.getInstance().nextId(),
                phoneNumber,
                null,
                email);
        personList.add(person);
    }

    public IPerson getPersonById(int personId){
        for (IPerson person : personList){
            if (person.getCreditID() == personId){
                return person;
            }
        }
        return null;
    }


    public ObservableList<IPerson> getPersonList(){
        return personList;
    }



    /**
     * Searches through the list of persons to return the found people.
     *
     * @param findPerson
     * @return
     */
    public ArrayList<IPerson> searchPerson(String findPerson) {
        return DatabaseLoader.getInstance().searchQueryToPersonList(findPerson);
    }

}
