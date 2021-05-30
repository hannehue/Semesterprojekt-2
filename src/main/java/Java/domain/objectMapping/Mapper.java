package Java.domain.objectMapping;

import Java.domain.data.*;
import Java.interfaces.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Mapper {
    private static Mapper instance;
    private SimpleDateFormat formatter;

    private Mapper() {
        formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
    }

    static Mapper getInstance() {
        if (instance == null) {
            instance = new Mapper();
        }
        return instance;
    }

    /**
     * Construct a person from a resultSet.
     * @param personResultSet
     * @return
     */
    IPerson mapPerson(ResultSet personResultSet) {
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

    IJob mapJob(ResultSet jobResultSet) {
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

    IMovie mapMovie(ResultSet movieResultSet) {

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
        } catch (SQLException | ParseException e) {
            System.out.println("WENT WRONG AT MAPPING MOVIE");
            e.printStackTrace();
        }


        return null;
    }

    IShow mapShow(ResultSet showResultSet) {
        try {
            return new Show(
                    /* Name         */ showResultSet.getString("name"),
                    /* Date         */ formatter.parse(showResultSet.getString("date_added")),
                    /* CreditID     */ showResultSet.getInt("credit_id"),
                    /* Approved     */ showResultSet.getBoolean("approved"),
                    /* description  */ showResultSet.getString("description"),
                    /* IsAllSeasonsApproved*/ showResultSet.getBoolean("all_seasons_approved")
            );

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    ISeason mapSeason(ResultSet seasonResultSet) {
        try {
            return new Season(
                    /* Name         */ seasonResultSet.getString("name"),
                    /* Date         */ formatter.parse(seasonResultSet.getString("date_added")),
                    /* CreditID     */ seasonResultSet.getInt("credit_id"),
                    /* Approved     */ seasonResultSet.getBoolean("approved"),
                    /* description  */ seasonResultSet.getString("description"),
                    /* showID       */ seasonResultSet.getInt("show_id"),
                    /* AllEpisodesApproved*/ seasonResultSet.getBoolean("all_episodes_approved")
            );
        } catch (SQLException | ParseException e) {
            System.out.println("WENT WRONG AT MAPPING SEASON");
            e.printStackTrace();
        }
        return null;
    }

    IEpisode mapEpisode(ResultSet episodeResultSet){

        try {
                return new Episode(
                        /* Name         */ episodeResultSet.getString("name"),
                        /* Date         */ formatter.parse(episodeResultSet.getString("date_added")),
                        /* CreditID     */ episodeResultSet.getInt("credit_id"),
                        /* Approved     */ episodeResultSet.getBoolean("approved"),
                        /* description  */ episodeResultSet.getString("description"),
                        /* productionID */ episodeResultSet.getInt("production_id"),
                        /* category     */ new Category[]{Category.values()[episodeResultSet.getInt("category_id") - 1]},
                        /* lengthInSecs */ episodeResultSet.getInt("length_in_secs"),
                        /* release_date */ formatter.parse(episodeResultSet.getString("release_date")),
                        /* seasonID     */ episodeResultSet.getInt("season_id")
                );

        } catch (SQLException | ParseException e){
            System.out.println("WENT WRONG AT MAPPING EPISODE");
            e.printStackTrace();
        }

        return null;
    }

}
