package Java.data;

import Java.domain.*;
import Java.interfaces.*;

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
        /*
        personFile = new File(DatabaseLoader.class.getClassLoader().getResource("Persons.txt").getFile());
        groupFile = new File(DatabaseLoader.class.getClassLoader().getResource("Groups.txt").getFile());
        movieFile = new File(DatabaseLoader.class.getClassLoader().getResource("Movies.txt").getFile());
        showFile = new File(DatabaseLoader.class.getClassLoader().getResource("Shows.txt").getFile());
        */


        //Create connection to database
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            this.connection = DriverManager.getConnection(
                    "jdbc:postgresql://hattie.db.elephantsql.com:5432/bpmfwbjk",
                    "bpmfwbjk" ,
                    /** TILFØJ PASSWORD HER **/
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

    /** Disse 3 vil jeg gerne rykke i en (facade) klasse for sig selv, sammen med de andre der kommer -Hans **/

    public IPerson queryToPerson(String searchString) {
        IPerson tempPerson = null;
        try {
            PreparedStatement queryStatement = getInstance().connection.prepareStatement(
                "SELECT * FROM credits, persons WHERE LOWER(name) LIKE LOWER(?)"
            );
            queryStatement.setString(1, "%" + searchString + "%");
            ResultSet queryResult = queryStatement.executeQuery();
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

    public IMovie queryToMovie(String[] strings){
        IMovie tempMovie = null;
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
        //getInstance().queryToPerson("Jens");
        System.out.println(getInstance().queryToPerson("Je").getName());
        System.out.println(getInstance().queryToPerson("je").getPersonID());

        /*
        try {
            PreparedStatement statement = getInstance().connection.prepareStatement("SELECT * From persons, credits");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString("name") + "\t" +
                        resultSet.getString("credit_id") + "\t" + resultSet.getString("person_id"));
            }


        } catch (SQLException e) {
            System.out.println("Went wrong at prepared Statement");
            e.printStackTrace();
        }

         */
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
