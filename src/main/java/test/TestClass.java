package test;

import Java.persistence.DatabaseLoaderFacade;
import Java.domain.services.PersonManager;
import Java.interfaces.IPerson;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestClass {
    DatabaseLoaderFacade database = DatabaseLoaderFacade.getInstance();
    String testPersonName = "testperson";
    String testPersonDesc = "testDesc";
    String testPersonPhone = "88888888";
    String testPersonInfo = "personalInfoString";
    String testPersonEmail = "email@string.com";
    int personId;

    @Test
    public void testPersonDatabaseTransaction(){
        PersonManager.getInstance().addPerson(
                testPersonName,
                testPersonDesc,
                testPersonPhone,
                testPersonInfo,
                testPersonEmail
        );

        ArrayList<IPerson> personResults = PersonManager.getInstance().searchPerson(testPersonName);

        for (IPerson person : personResults){
            if (person.getPhoneNumber().equals(testPersonPhone)){
                assertEquals(person.getName(), testPersonName);
                assertEquals(person.getDescription(), testPersonDesc);
                assertEquals(person.getPhoneNumber(), testPersonPhone);
                assertEquals(person.getPersonalInfo(), testPersonInfo);
                assertEquals(person.getPersonEmail(), testPersonEmail);
                personId = person.getCreditID();
                assertNotEquals(personId,0);
                try {
                    database.deleteCredit(person);
                    System.out.println("Removed person with creditID " + personId);
                } catch (Exception e){
                    fail();
                }
            }
        }
    }
}
