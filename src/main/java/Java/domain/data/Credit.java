package Java.domain.data;

import Java.interfaces.ICredit;

import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Credit implements ICredit {

    private String name;
    private Date dateAdded;
    private int creditID;
    private boolean approved;
    private String description;
    private Map<String, Integer> IDMap;


    public Credit(String name, Date dateAdded, int creditID, boolean approved, String description){
        this.name = name;
        this.dateAdded = dateAdded;
        this.creditID = creditID;
        this.approved = approved;
        this.description = description;
    }

/*
    @Override
    public String toString() {
        //Opretter en ny stringbuilder for hver credit der er blevet returneret
        StringBuilder stringBuilder = new StringBuilder();
        //Splitter ved ","
        String[] observableResultsString = this.toString().split(",");
        //Tilføj hver linje der er blevet splittet til string builder
        for (String s: observableResultsString) { stringBuilder.append(s).append("\n"); }
        //tilføj til liste
        return stringBuilder.toString();
    }

 */

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Date getDateAdded() {
        return dateAdded;
    }

    @Override
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public int getCreditID() {
        return creditID;
    }

    @Override
    public void setCreditID(int creditID) {
        this.creditID = creditID;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean isApproved() {
        return approved;
    }

    @Override
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public Map<String, Integer> getIDMap() {
        return IDMap;
    }

    @Override
    public void setIDMap(Map<String, Integer> IDs) {
        this.IDMap = IDs;
    }

    @Override
    public String buildView() {
        //Opretter en ny stringbuilder for hver credit der er blevet returneret
        StringBuilder stringBuilder = new StringBuilder();
        //Splitter ved ","
        String[] observableResultsString = this.toString().split(",");

        //Tilføj hver linje der er blevet splittet til string builder
        for (String s: observableResultsString) {
            Pattern p = Pattern.compile("\\b.{1," + (80-1) + "}\\b\\W?");
            Matcher m = p.matcher(s);
            while (m.find()){
                stringBuilder.append(m.group()).append("\n");
            }
        }
        //tilføj til liste
        return stringBuilder.toString();
    }
}
