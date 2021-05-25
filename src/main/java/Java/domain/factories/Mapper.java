package Java.domain.factories;

import Java.domain.data.*;
import Java.interfaces.IJob;
import Java.interfaces.IMovie;
import Java.interfaces.IPerson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Mapper {
    private static Mapper instance;
    private SimpleDateFormat formatter;

    private Mapper() {
        formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
    }

    public static Mapper getInstance() {
        if (instance == null) {
            instance = new Mapper();
        }
        return instance;
    }

    public IPerson mapPerson(ResultSet personResultSet) {
        try {
            IPerson tempPerson = new Person(
                    /* Name         */ personResultSet.getString("name"),
                    /* Date         */ formatter.parse(personResultSet.getString("date_added")),
                    /* CreditID     */ personResultSet.getInt("credit_id"),
                    /* Approved     */ personResultSet.getBoolean("approved"),
                    /* description  */ personResultSet.getString("description"),
                    /* personID     */ personResultSet.getInt("person_id"),
                    /* phone number */ personResultSet.getString("phone_number"),
                    /* personal info*/ personResultSet.getString("personal_info"),
                    /* email        */ personResultSet.getString("email")
            );
            return tempPerson;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IJob mapJob(ResultSet jobResultSet) {
        try {
            // Job initialization
            // Checks whether the current job belongs to the current tempPerson, from the first query
            //if (person.getPersonID() == jobResultSet.getInt("person_id")) {
//                        if (jobResult.getInt("job_role_id") != 1) {
            return new Job(
                    /* PersonID         */        jobResultSet.getInt("person_id"),
                    /* Role from roleID */ ((Role.values()[jobResultSet.getInt("job_role_id") - 1])),
                    /* ProductionID     */ jobResultSet.getInt("production_id"));
//                        } else {
//                            jobs.add(new Job(
//                                    /* PersonID         */        jobResult.getInt("person_id"),
//                                    /* Role from roleID */ ((Role.values()[jobResult.getInt("job_role_id") - 1])),
//                                    /* Character name   */ /* need name in database */
//                                    /* ProductionID     */ jobResult.getInt("production_id")));
//                        }
            //}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IMovie mapMovie(ResultSet movieResultSet){

        try {
                IMovie movie = new Movie(
                        /* Name         */ movieResultSet.getString("name"),
                        /* Date         */ formatter.parse(movieResultSet.getString("date_added")),
                        /* CreditID     */ movieResultSet.getInt("credit_id"),
                        /* Approved     */ movieResultSet.getBoolean("approved"),
                        /* description  */ movieResultSet.getString("description"),
                        /* productionID */ movieResultSet.getInt("production_id"),
                        /** Genovervej **/
                        /* category     */ new Category[]{Category.values()[movieResultSet.getInt("category_id") - 1]},
                        /** .... **/
                        /* lengthInSecs */ movieResultSet.getInt("length_in_secs"),
                        /* Releasedate  */ formatter.parse(movieResultSet.getString("release_date"))
                );
            return movie;
        } catch (SQLException | ParseException e){
            System.out.println("WENT WRONG AT MAPPING MOVIE");
            e.printStackTrace();
        }


        return null;
    }
}
