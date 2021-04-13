package Java;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class DatabaseLoader {

    private Scanner inputStream = null;
    private FileWriter outputStream = null;

    private File personFile;
    private ArrayList<String[]> personArraylist;

    private SimpleDateFormat formatter;

    public DatabaseLoader() throws IOException, ParseException {
        formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        personFile = new File(DatabaseLoader.class.getClassLoader().getResource("Persons.txt").getFile());
        inputStream = new Scanner(personFile);

        personArraylist = readPersons();
    }

    public void writePersons(ArrayList list) throws IOException {
        outputStream = new FileWriter(personFile, false);

        for(int row = 0; row < personArraylist.size(); row++){
            String line = "";
            for(int column = 0; column < personArraylist.get(row).length; column++){
                line += personArraylist.get(row)[column] + ",";
            }
            outputStream.append(line + "\n");
        }
        outputStream.close();
    }

    public ArrayList<String[]> readPersons() {

        ArrayList<String[]> personList = new ArrayList<>();
        while (inputStream.hasNext()){
            personList.add(inputStream.nextLine().split(","));
        }
        return personList;
    }

    public Person stringsToPerson(String[] vals){
        Person tempPerson = null;
        try{
            tempPerson = new Person(vals[0], formatter.parse(vals[1]),Integer.parseInt(vals[2]),
                                    Boolean.parseBoolean(vals[3]),vals[4],Integer.parseInt(vals[5]),vals[6],vals[7],vals[8]);
            if (vals[9].equals("null")){
                return tempPerson;
            }
        } catch (java.text.ParseException e){
            e.printStackTrace();
            System.err.println("Failed when initializing person from string array");
            return null;
        }
        // Loads one persons jobs into strings
        String[] jobsStrings = vals[9].split("##");
        ArrayList<Job> jobs = new ArrayList<>();

        // Job handling
        for (int i = 0; i < jobsStrings.length; i++) { // Loops over all jobs
            String[] jobValues = jobsStrings[i].split("--"); // Splits all values in the job
            String[] rolesStrings = jobValues[0].split(";"); // Splits all roles on the job (first value)

            Role[] roles = new Role[rolesStrings.length];

            for (int j = 0; j < roles.length; j++) {
                roles[j] = Role.getRoleFromString(rolesStrings[j]);
            }

            String[] characterNames = jobValues[2].split(";");
            if(jobValues[2].equals("null")){
                characterNames = null;
            }

            jobs.add(new Job(roles,Integer.parseInt(jobValues[1]),characterNames));

        }

        tempPerson.setJobs(jobs);
        return tempPerson;
    }

    public static void main(String[] args) throws IOException, ParseException {
        DatabaseLoader dbload = new DatabaseLoader();

        System.out.println(dbload.stringsToPerson(dbload.personArraylist.get(0)));
        System.out.println(dbload.stringsToPerson(dbload.personArraylist.get(1)));
    }

}
