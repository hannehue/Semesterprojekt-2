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
        String[] bla = ("aaPesdaasdater Jens,Sun Apr 11 14:56:59 CEST 2021,3,false,Peter er ikke easasdasdn kameramand,4,21222123," +
                "Det her hdsasadadsar Peter selv skrevet,Peter@test.com,Lydmand$$(badehotelletID)$$null||Lydmand$$(minkavlerne)$$null").split(",");
        personArraylist.add(bla);
        writePersons(personArraylist);

        //System.out.println(formatter.parse(new Date().toString()));
    }

    public void writePersons(ArrayList list) throws IOException {
        outputStream = new FileWriter(personFile, false);


        for(int row = 0; row < personArraylist.size(); row++){
            String line = "";
            for(int column = 0; column < personArraylist.get(row).length; column++){
                line += personArraylist.get(row)[column] + ",";
            }
            System.out.println(line);
            outputStream.append(line + "\n");
        }
        outputStream.flush();
        outputStream.close();

    }

    public ArrayList<String[]> readPersons() {

        ArrayList<String[]> personList = new ArrayList<>();
        while (inputStream.hasNext()){
            personList.add(inputStream.nextLine().split(","));
        }
        return personList;
    }


    public static void main(String[] args) throws IOException, ParseException {
        DatabaseLoader dbload = new DatabaseLoader();

    }

}
