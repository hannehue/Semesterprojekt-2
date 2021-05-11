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


    private DatabaseLoader() throws IOException {

        formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        /*
        personFile = new File(DatabaseLoader.class.getClassLoader().getResource("Persons.txt").getFile());
        groupFile = new File(DatabaseLoader.class.getClassLoader().getResource("Groups.txt").getFile());
        movieFile = new File(DatabaseLoader.class.getClassLoader().getResource("Movies.txt").getFile());
        showFile = new File(DatabaseLoader.class.getClassLoader().getResource("Shows.txt").getFile());
        */
        personArraylist = readCredits(personFile);
        groupArraylist = readCredits(groupFile);
        movieArrayList = readCredits(movieFile);
        showArrayList = readCredits(showFile);


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

    public static DatabaseLoader getInstance() throws IOException {
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

    public ArrayList<String[]> readCredits(File file) throws FileNotFoundException {
        inputStream = new Scanner(file);
        ArrayList<String[]> creditList = new ArrayList<>();
        while (inputStream.hasNext()) {
            creditList.add(inputStream.nextLine().split(","));
        }
        return creditList;
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

    public IPerson queryToPerson(String[] vals) {
        IPerson tempPerson = null;
        try {
            if (vals[1].equals("null")){
                tempPerson = new Person(vals[0], null, Integer.parseInt(vals[2]),
                        Boolean.parseBoolean(vals[3]), vals[4], Integer.parseInt(vals[5]), vals[6], vals[7], vals[8]);
            } else {
                tempPerson = new Person(vals[0], formatter.parse(vals[1]), Integer.parseInt(vals[2]),
                        Boolean.parseBoolean(vals[3]), vals[4], Integer.parseInt(vals[5]), vals[6], vals[7], vals[8]);
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            System.err.println("Failed when initializing person from string array");
            return null;
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

    public static void main(String[] args) throws IOException {

        try {
            PreparedStatement statement = getInstance().connection.prepareStatement("SELECT * From categories");
            ResultSet resultSet = statement.executeQuery();
            System.out.println("ID \tCategory");
            while (resultSet.next()){
                System.out.println(resultSet.getString("category_id") + "\t" + resultSet.getString("category"));
            }


        } catch (SQLException e) {
            System.out.println("Went wrong at prepared Statement");
            e.printStackTrace();
        }

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
