package Java.data;

import Java.domain.data.*;
import Java.interfaces.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.*;
import java.sql.*;

public class DatabaseLoader {

    private static DatabaseLoader instance;

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

    private SimpleDateFormat formatter;

    private Connection connection;


    private DatabaseLoader(){

        formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

        //Create connection to database
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            this.connection = DriverManager.getConnection(
                    "jdbc:postgresql://hattie.db.elephantsql.com:5432/bpmfwbjk",
                    "bpmfwbjk" ,
                    "Q2w3lGuOmhrrTotMtUu8dyp6Hh4tbbl6");
        } catch (SQLException e) {
            System.out.println("Went wrong at connection creation");
            e.printStackTrace();
        }


    }

    public static DatabaseLoader getInstance(){
        if (instance == null){
            instance = new DatabaseLoader();
        }
        return instance;
    }

    public void writeCredits(File file, ArrayList<String[]> creditList) throws IOException {
        outputStream = new FileWriter(file, false);

        for (int row = 0; row < creditList.size(); row++) {
            outputStream.write(stringArraytoString(creditList.get(row)) + "\n");
        }
        outputStream.close();
    }

    public void writeAllCredits() throws IOException {
        for (int i = 0; i < 4; i++) {
            writeCredits(personFile,personArraylist);
            writeCredits(groupFile, groupArraylist);
            writeCredits(movieFile, movieArrayList);
            writeCredits(showFile,showArrayList);
        }
    }

    public String stringArraytoString(String[] strings){
        String line = "";
        for (int column = 0; column < strings.length; column++) {
            line += strings[column] + ",";
        }
        return line;
    }

    public String[] creditToStringArray(ICredit credit) {

        return credit.toFileString().split(",");
    }

    public void addCreditToDatabase(ICredit credit){
        if (credit instanceof IPerson) {
            personArraylist.add(creditToStringArray(credit));

        } else if (credit instanceof IMovie){
            movieArrayList.add(creditToStringArray(credit));

        } else if (credit instanceof IGroup){
            groupArraylist.add(creditToStringArray(credit));

        } else if (credit instanceof IShow){
            showArrayList.add(creditToStringArray(credit));
        }
    }

    public void addCreditsToDatabase(ArrayList<? extends ICredit> readList){
        if(readList.size() == 0 || readList == null){
            return;
        }

        ArrayList<String[]> tempList = new ArrayList<>();
        for (ICredit p: readList){
            if (p != null){
                tempList.add(creditToStringArray(p));
            }
        }
        if (readList.get(0) instanceof IPerson) {
            personArraylist = tempList;

        } else if (readList.get(0) instanceof IMovie){
            movieArrayList = tempList;

        } else if (readList.get(0) instanceof IGroup){
            groupArraylist = tempList;
        } else if (readList.get(0) instanceof IShow){
            showArrayList = tempList;
        }
    }

    public IPerson searchQueryToPerson(String searchString) {
        //Deaclare person to be returned
        IPerson tempPerson = null;
        //Query & person creation try block
        try {
            //PreparedStatement that gets all results from credits, persons, jobs & job_roles tables where a name looks
            //like the search string, case insensitive
            PreparedStatement queryStatement = getInstance().connection.prepareStatement(
                "SELECT * FROM credits, persons, jobs, job_roles WHERE LOWER(name) LIKE LOWER(?)"
            );
            //inserts searchString into SQL statement
            queryStatement.setString(1, "%" + searchString + "%");
            //gets a SINGLE result set that matches query. This most likely need to be reworked!!!!
            ResultSet queryResult = queryStatement.executeQuery();
            //Instantiates a new person in the tempPerson variable
            while (queryResult.next()) {
                tempPerson = new Person(
                        /* Name         */ queryResult.getString("name"),
                        /* Date         */ formatter.parse(queryResult.getString("date_added")),
                        /* CreditID     */ queryResult.getInt("credit_id"),
                        /* Approved     */ queryResult.getBoolean("approved"),
                        /* description  */ queryResult.getString("description"),
                        /* personID     */ queryResult.getInt("person_id"),
                        /* phone number */ queryResult.getString("phone_number"),
                        /* personal info*/ queryResult.getString("personal_info"),
                        /* email        */ queryResult.getString("email")
                );
                //JobList for person. ALSO NEEDS TO BE REWORKED FOR EVENTUAL MULTIPLE JOBS
                ArrayList<IJob> jobs = new ArrayList<>();
                jobs.add(new Job(queryResult.getInt("person_id"), ((Role.values()[queryResult.getInt("job_role_id") - 1])), queryResult.getInt("production_id")));
                tempPerson.setJobs(jobs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("SQL ERROR at queryToPerson");
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Parse error at queryToPerson");
        }
        return tempPerson;
    }

    //Needs to be implemented to return an arraylist of matching searches
    public ObservableList<IPerson> searchQueryToPersonList(String searchString) {
        ObservableList<IPerson> personObservableList = FXCollections.observableArrayList();
        try {
            PreparedStatement queryStatement = getInstance().connection.prepareStatement(
                    "SELECT * FROM credits, persons WHERE LOWER(name) LIKE LOWER(?)"
            );
            queryStatement.setString(1, "%" + searchString + "%");
            queryStatement.execute();
            while (queryStatement.getResultSet().next()) {
                ResultSet queryResult = queryStatement.getResultSet();
                personObservableList.add(new Person(
                        /* Name         */ queryResult.getString("name"),
                        /* Date         */ formatter.parse(queryResult.getString("date_added")),
                        /* CreditID     */ queryResult.getInt("credit_id"),
                        /* Approved     */ queryResult.getBoolean("approved"),
                        /* description  */ queryResult.getString("description"),
                        /* personID     */ queryResult.getInt("person_id"),
                        /* phone number */ queryResult.getString("phone_number"),
                        /* personal info*/ queryResult.getString("personal_info"),
                        /* email        */ queryResult.getString("email")
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("SQL ERROR at queryToPerson");
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Parse error at queryToPerson");
        }
        return personObservableList;
    }

    public IGroup queryToGroup(String[] strings){
        IGroup tempGroup = null;
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

    public IMovie searchQueryToMovie(String searchString){
        try {
            IMovie tempMovie = null;
            PreparedStatement queryStatement = getInstance().connection.prepareStatement(
                    "SELECT * FROM credits, movies, productions, categories WHERE LOWER(name) LIKE LOWER(?)"
            );
            //inserts searchString into SQL statement
            queryStatement.setString(1, "%" + searchString + "%");
            //gets a SINGLE result set that matches query. This most likely need to be reworked!!!!
            ResultSet queryResult = queryStatement.executeQuery();

            while (queryResult.next()) {
                tempMovie = new Movie(
                        /* Name         */ queryResult.getString("name"),
                        /* Date         */ formatter.parse(queryResult.getString("date_added")),
                        /* CreditID     */ queryResult.getInt("credit_id"),
                        /* Approved     */ queryResult.getBoolean("approved"),
                        /* description  */ queryResult.getString("description"),
                        /* productionID */ queryResult.getInt("production_id"),
                        /** Genovervej **/
                        /* category     */ new Category[]{Category.values()[queryResult.getInt("category_id") - 1]},
                        /** .... **/
                        /* lengthInSecs */ queryResult.getInt("length_in_secs"),
                        /* Releasedate  */ formatter.parse(queryResult.getString("release_date"))
                        );
            }
            return tempMovie;

        } catch (ParseException e){
            e.printStackTrace();
            System.err.println("Failed when initializing movie search string");
            return null;
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("SQL Exception at search query to Movie");
        }
        return null;
    }

    public IEpisode queryToEpisode(String[] strings){
        IEpisode tempEpisode = null;
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
            System.err.println("Failed when initializing episode from string array");
            return null;
        }
        return tempEpisode;
    }

    public static void main(String[] args){
        System.out.println(getInstance().searchQueryToMovie("ow").getName());
    }


    private Connection getConnection() {
        return connection;
    }

    public ArrayList<String[]> getPersonArraylist() {
        return personArraylist;
    }

    public ArrayList<String[]> getGroupArraylist() {
        return groupArraylist;
    }

    public ArrayList<String[]> getMovieArrayList() {
        return movieArrayList;
    }

    public ArrayList<String[]> getShowArrayList() {
        return showArrayList;
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
