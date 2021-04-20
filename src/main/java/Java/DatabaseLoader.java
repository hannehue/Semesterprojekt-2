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

    private File currentFile;

    private File personFile;
    private ArrayList<String[]> personArraylist;

    private File groupFile;
    private ArrayList<String[]> groupArraylist;

    private SimpleDateFormat formatter;

    public void setCurrentFile(int i) {
    }

    public DatabaseLoader() throws IOException, ParseException {
        formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        this.personFile = new File(DatabaseLoader.class.getClassLoader().getResource("Persons.txt").getFile());
        this.groupFile = new File(DatabaseLoader.class.getClassLoader().getResource("Groups.txt").getFile());
        personArraylist = readCredits(personFile);
        groupArraylist = readCredits(groupFile);
    }



    public void writeCredits(File file, ArrayList<String[]> creditList) throws IOException {
        outputStream = new FileWriter(file, false);

        for (int row = 0; row < creditList.size(); row++) {
            outputStream.append(stringArraytoString(creditList.get(row)) + "\n");
        }
        outputStream.close();
    }

    public String stringArraytoString(String[] strings){
        String line = "";
        for (int column = 0; column < strings.length; column++) {
            line += strings[column] + ",";
        }
        return line;
    }

    public ArrayList<String[]> readCredits(File file) throws FileNotFoundException {
        inputStream = new Scanner(file);
        ArrayList<String[]> personList = new ArrayList<>();
        while (inputStream.hasNext()) {
            personList.add(inputStream.nextLine().split(","));
        }
        return personList;
    }



    public String[] creditToStringArray(Credit credit) {

        String[] creditArray = credit.toFileString().split(",");
        return creditArray;
    }

    public void addCredit(Credit credit, ArrayList<Credit> creditsList){
        creditsList.add(credit);
    }

    public void addCredits(ArrayList<? extends Credit> readList, ArrayList<String[]> writeList ){
        for (Credit credit: readList){
            writeList.add(creditToStringArray(credit));
        }
    }

    /** Disse to vil jeg gerne rykke i en (facade) klasse for sig selv, sammen med de andre der kommer -Hans **/

    public Person stringsToPerson(String[] vals) {
        Person tempPerson = null;
        try {
            tempPerson = new Person(vals[0], formatter.parse(vals[1]), Integer.parseInt(vals[2]),
                    Boolean.parseBoolean(vals[3]), vals[4], Integer.parseInt(vals[5]), vals[6], vals[7], vals[8]);
            if (vals[9].equals("null")) {
                return tempPerson;
            }
        } catch (java.text.ParseException e) {
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
            if (jobValues[2].equals("null")) {
                characterNames = null;
            }

            jobs.add(new Job(roles, Integer.parseInt(jobValues[1]), characterNames));

        }

        tempPerson.setJobs(jobs);
        return tempPerson;
    }

    public Group stringsToGroup(String[] strings){
        Group tempGroup = null;
        try {
            tempGroup = new Group(strings[0], formatter.parse(strings[1]), Integer.parseInt(strings[2]),
                    Boolean.parseBoolean(strings[3]), strings[4], Integer.parseInt(strings[5]));
        } catch (ParseException e){
            e.printStackTrace();
            System.err.println("Failed when initializing group from string array");
            return null;
        }
        return tempGroup;
    }

    public static void main(String[] args) throws IOException, ParseException {
        DatabaseLoader dbload = new DatabaseLoader();
        dbload.writeCredits(dbload.groupFile, dbload.groupArraylist);
        //System.out.println(dbload.stringsToPerson(dbload.personArraylist.get(0)));
        System.out.println(dbload.stringsToPerson(dbload.personArraylist.get(1)));

        //dbload.personToString(dbload.stringsToPerson(dbload.personArraylist.get(0)));

        System.out.println(dbload.stringsToGroup(dbload.groupArraylist.get(0)));
    }


    public ArrayList<String[]> getPersonArraylist() {
        return personArraylist;
    }
}
