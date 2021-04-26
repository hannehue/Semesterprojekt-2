package Java;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class DatabaseLoader {

    private Scanner inputStream = null;
    private FileWriter outputStream = null;

    private File personFile;
    private ArrayList<String[]> personArraylist;

    private File groupFile;
    private ArrayList<String[]> groupArraylist;

    private File movieFile;
    private ArrayList<String[]> movieArrayList;

    private File showFile;
    private ArrayList<String[]> showArrayList;

    private File episodesFile;
    private ArrayList<String[]> episodeArrayList;


    private SimpleDateFormat formatter;

    public DatabaseLoader() throws IOException, ParseException {
        formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        personFile = new File(DatabaseLoader.class.getClassLoader().getResource("Persons.txt").getFile());
        groupFile = new File(DatabaseLoader.class.getClassLoader().getResource("Groups.txt").getFile());
        movieFile = new File(DatabaseLoader.class.getClassLoader().getResource("Movies.txt").getFile());
        episodesFile = new File(DatabaseLoader.class.getClassLoader().getResource("Episodes.txt").getFile());
        showFile = new File(DatabaseLoader.class.getClassLoader().getResource("Shows.txt").getFile());
        personArraylist = readCredits(personFile);
        groupArraylist = readCredits(groupFile);
        movieArrayList = readCredits(movieFile);
        showArrayList = readCredits(showFile);
        episodeArrayList = readCredits(episodesFile);
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
        ArrayList<String[]> creditList = new ArrayList<>();
        while (inputStream.hasNext()) {
            creditList.add(inputStream.nextLine().split(","));
        }
        return creditList;
    }

    public String[] creditToStringArray(Credit credit) {
        String[] creditArray = credit.toFileString().split(",");
        return creditArray;
    }

    public void addCreditToDatabase(Credit credit){
        if (Person.class.equals(credit.getClass())) {
            personArraylist.add(creditToStringArray(credit));

        } else if (Movie.class.equals(credit.getClass())){
            movieArrayList.add(creditToStringArray(credit));

        } else if (Group.class.equals(credit.getClass())){
            groupArraylist.add(creditToStringArray(credit));

        } else if (Show.class.equals(credit.getClass())){
            showArrayList.add(creditToStringArray(credit));
        }
    }

    public void addCreditsToDatabase(ArrayList<? extends Credit> readList){
        for (Credit credit : readList){
            addCreditToDatabase(credit);
        }
    }

    /** Disse 3 vil jeg gerne rykke i en (facade) klasse for sig selv, sammen med de andre der kommer -Hans **/

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

            String[] characterNames = jobValues[2].split(";");
            if (jobValues[2].equals("null")) {
                characterNames = null;
                jobs.add(new Job(Role.getRoleFromString(jobValues[0]), Integer.parseInt(jobValues[1])));
            }
            else {
                jobs.add(new Job(Role.getRoleFromString(jobValues[0]), characterNames[0], Integer.parseInt(jobValues[1])));
            }


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

    public Movie stringsToMovie(String[] strings){
        Movie tempMovie = null;
        try {
            tempMovie = new Movie(strings[0], formatter.parse(strings[1]), Integer.parseInt(strings[2]),
                    Boolean.parseBoolean(strings[3]), strings[4], Integer.parseInt(strings[5]),
                    Category.getCategoriesFromString(strings[6]), Integer.parseInt(strings[7]), formatter.parse(strings[8])
                    );
            for (String staff : new ArrayList<>(Arrays.asList(strings[9].split(";"))) ) {
                tempMovie.addStaffID(Integer.parseInt(staff));
            }
        } catch (ParseException e){
            e.printStackTrace();
            System.err.println("Failed when initializing movie from string array");
            return null;
        }
        return tempMovie;
    }

    public Episode stringsToEpisode(String[] strings){
        Episode tempEpisode = null;
        try {
            tempEpisode = new Episode(strings[0], formatter.parse(strings[1]), Integer.parseInt(strings[2]),
                    Boolean.parseBoolean(strings[3]), strings[4], Integer.parseInt(strings[5]),
                    Category.getCategoriesFromString(strings[6]), Integer.parseInt(strings[7]), formatter.parse(strings[8])
                    , Integer.parseInt(strings[10]));

            for (String staff : new ArrayList<String>(Arrays.asList(strings[9].split(";")))) {
                tempEpisode.addStaffID(Integer.parseInt(staff));
            }

        } catch (ParseException e){
            e.printStackTrace();
            System.err.println("Failed when initializing movie from string array");
            return null;
        }
        return tempEpisode;
    }

    public static void main(String[] args) throws IOException, ParseException {
        //Production kan lige nu kun have en category, skal laves om
        DatabaseLoader dbload = new DatabaseLoader();
        for (String[] arr : dbload.groupArraylist) {
            System.out.println(dbload.stringsToGroup(arr).toString());
        }
        //dbload.groupArraylist.add(dbload.creditToStringArray(new Group("Et eller andet band 2", new Date(),2,false,"Band fra Esbjerg",3)));
        //dbload.writeCredits(dbload.groupFile, dbload.groupArraylist);

        System.out.println(dbload.stringsToEpisode(dbload.episodeArrayList.get(0)));
    }

    public ArrayList<String[]> getPersonArraylist() {
        return personArraylist;
    }

    public File getPersonFile() {
        return personFile;
    }

    public File getGroupFile() {
        return groupFile;
    }

    public File getMovieFile() {
        return movieFile;
    }



}
