package Java.domain.dataoperators;

import Java.domain.data.Person;
import Java.interfaces.ICredit;
import Java.interfaces.IPerson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;

public class PersonOperator {
    private static PersonOperator instance = new PersonOperator();
    private PersonOperator(){}

    public static PersonOperator getInstance() {
        return instance;
    }

    private final ObservableList<IPerson> personList = FXCollections.observableArrayList();

    public void addPerson(String name, String description, String phoneNumber, String email) {
        IPerson person = new Person(
                name,
                new Date(),
                1, // change later
                false,
                description,
                1,
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
        return  personList;
    }



    /**
     * Searches through the list of persons to return the found people.
     *
     * @param findPerson
     * @return
     */
    public ArrayList<ICredit> searchPerson(String findPerson) {
        ArrayList<ICredit> creditList = new ArrayList<>();
        for (ICredit person : getPersonList()) {
            if (person.getName().toLowerCase().contains(findPerson)) {
                creditList.add(person);
            }
        }
        return creditList;
    }

}
