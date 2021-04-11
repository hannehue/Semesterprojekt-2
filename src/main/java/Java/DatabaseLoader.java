package Java;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class DatabaseLoader {

    private Scanner inputStream = null;
    private FileWriter outputStream = null;

    private File personFile;
    private String personFileString;

    private SimpleDateFormat formatter;

    public DatabaseLoader() throws IOException, ParseException {
        formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

        personFile = new File(getClass().getClassLoader().getResource("Persons.csv").getPath());
        inputStream = new Scanner(personFile);
        outputStream = new FileWriter(personFile, false);

        personFileString = readPersons();
        System.out.println(personFileString);
        //System.out.println(formatter.parse(new Date().toString()));
    }

    public void writePersons(ArrayList list) throws IOException {
    }

    public String readPersons() throws IOException {
        String line = "";
        while (inputStream.hasNext()) {
            System.out.println(inputStream.nextLine());
            //line += inputStream.nextLine();
        }
        return line;
    }

}
