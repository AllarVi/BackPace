package application;

import application.profile.Goal;
import application.profile.GoalRepository;
import application.profile.Goals;
import application.profile.GoalsRepository;
import application.team.ShortTableRow;
import application.team.ShortTableRowRepository;
import application.team.ShortTeamView;
import application.team.ShortTeamViewRepository;
import application.user.PaceUser;
import application.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Spring Boot main application.Application
 */
@SpringBootApplication
public class Application {

    public static final String HTTP_GRAPH_FACEBOOK_COM_ALLAR_PICTURE_TYPE_LARGE = "http://graph.facebook" + "" +
            ".com/1273703759309879/picture?type=large";
    public static final String ALLAR_ACCESS_TOKEN =
            "EAAD08lC2fhMBAIWW7X6j85X0s8IREqlmaXlV47g5NZBOsk22L616ooPDtmUCD7Rup7vVkmKAFP5k5y5zNbf0ZBZB0XGU2fbvaUx7uUxLfY3lStaOyCoo3SiVn9kNGTW5NIon6JC2BNspoLex6NfCBZBkgEZCAyfn0JbICsgpuLu0FTO2zcEsiULORo2nnZBLMZD";

    public static final String ALLAR_USER_ID = "1273703759309879";


    public static String ROOT = "upload-dir";

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class);
    }

    /**
     * We can do our initialization logic and testing here.
     */
    @Bean
    public CommandLineRunner user(UserRepository userRepository, ShortTableRowRepository shortTableRowRepository,
                                  ShortTeamViewRepository shortTeamViewRepository, GoalRepository goalRepository,
                                  GoalsRepository goalsRepository) {
        return (args) -> {
            createFileUploadDirectory();

            PaceUser paceUserAllar = getPaceUserAllar();

//            @@@@@ initGoals @@@@@
            initGoals(goalRepository);

            Goals goals = new Goals();
            Map<String, ArrayList<Goal>> goalMap = new HashMap<>();

            ArrayList<Goal> goalArrayList = (ArrayList<Goal>) getInitGoals();
            ArrayList<Goal> gymnasticsGoals = new ArrayList<>();
            ArrayList<Goal> cheerleadingGoals = new ArrayList<>();

            for (Goal goal : goalArrayList) {
                if (goal.getCategory().equals("gymnastics")) {
                    gymnasticsGoals.add(goal);
                } else if (goal.getCategory().equals("cheerleading")) {
                    cheerleadingGoals.add(goal);
                }
            }

            goalMap.put("gymnastics", gymnasticsGoals);
            goalMap.put("cheerleading", cheerleadingGoals);
            goals.setGoalMap(goalMap);

            goalsRepository.save(goals);

            paceUserAllar.setGoals(goals);

//            @@@@@ Kossurühm @@@@@
            ShortTeamView shortTeamViewKossuryhm = new ShortTeamView();
            shortTeamViewKossuryhm.setTeamName("Kossurühm");

            ArrayList<ShortTableRow> shortTableRowsKossuryhm = getShortTableRowsKossyryhm(shortTableRowRepository);
            List<ShortTableRow> shortTableRowListKossuryhm = new ArrayList<>(shortTableRowsKossuryhm);
            shortTeamViewKossuryhm.setShortTableRowMap(shortTableRowListKossuryhm);

//            @@@@@ Saltopoisid @@@@@
            ShortTeamView shortTeamViewSaltopoisid = new ShortTeamView();
            shortTeamViewSaltopoisid.setTeamName("Saltopoisid");

            ArrayList<ShortTableRow> shortTableRowsSaltopoisid = getShortTableRowsSaltopoisid(shortTableRowRepository);
            List<ShortTableRow> shortTableRowListSaltopoisid = new ArrayList<>(shortTableRowsSaltopoisid);
            shortTeamViewSaltopoisid.setShortTableRowMap(shortTableRowListSaltopoisid);

//            Save shortTeamView to database
            shortTeamViewRepository.save(shortTeamViewKossuryhm);
            shortTeamViewRepository.save(shortTeamViewSaltopoisid);

//            Add shortTeamView to user
            List<ShortTeamView> shortTeamViewList = new ArrayList<>();
//            shortTeamViewList.add(shortTeamViewKossuryhm);
            shortTeamViewList.add(shortTeamViewSaltopoisid);

            paceUserAllar.setShortTeamViewList(shortTeamViewList);

//            Save user to database
            userRepository.save(paceUserAllar);

            // fetch all customers
            log.info("Users found with findAll():");
            log.info("-------------------------------");
            for (PaceUser paceUser : userRepository.findAll()) {
                log.info(paceUser.toString());
            }
            log.info("");
        };
    }

    private PaceUser getPaceUserAllar() {
        PaceUser paceUserAllar = new PaceUser("Allar", ALLAR_USER_ID);
        paceUserAllar.setAccessToken(ALLAR_ACCESS_TOKEN);

        paceUserAllar.setPicture(HTTP_GRAPH_FACEBOOK_COM_ALLAR_PICTURE_TYPE_LARGE);
        return paceUserAllar;
    }

    private void createFileUploadDirectory() {
        boolean isDirCreated = new File(ROOT).mkdir();

        if (isDirCreated) {
            System.out.println("Directory created!");
        }
    }

    private void initGoals(GoalRepository goalRepository) {
        List<Goal> initGoals = getInitGoals();
        initGoals.forEach(goalRepository::save);
    }

    private ArrayList<ShortTableRow> getShortTableRowsKossyryhm(ShortTableRowRepository shortTableRowRepository) {
        ShortTableRow shortTableRow1 = new ShortTableRow(1, "Marin", "Gold", 1270);
        ShortTableRow shortTableRow2 = new ShortTableRow(2, "Marianne", "Gold", 1250);
        ShortTableRow shortTableRow3 = new ShortTableRow(3, "Maarika", "Gold", 1900);

        ArrayList<ShortTableRow> shortTableRows = new ArrayList<>();
        shortTableRows.add(shortTableRow1);
        shortTableRows.add(shortTableRow2);
        shortTableRows.add(shortTableRow3);

        shortTableRows.forEach(shortTableRowRepository::save);

        return shortTableRows;
    }

    private ArrayList<ShortTableRow> getShortTableRowsSaltopoisid(ShortTableRowRepository shortTableRowRepository) {
        ShortTableRow shortTableRow1 = new ShortTableRow(1, "Allar", "Gold", 1000);
        ShortTableRow shortTableRow2 = new ShortTableRow(2, "Rannar", "Gold", 980);
        ShortTableRow shortTableRow3 = new ShortTableRow(3, "Üllar", "Gold", 950);

        ArrayList<ShortTableRow> shortTableRows = new ArrayList<>();
        shortTableRows.add(shortTableRow1);
        shortTableRows.add(shortTableRow2);
        shortTableRows.add(shortTableRow3);

        shortTableRows.forEach(shortTableRowRepository::save);

        return shortTableRows;
    }

    private List<Goal> getInitGoals() {

        Goal goal1 = new Goal();
        goal1.setCategory("gymnastics");
        goal1.setTitle("Salto backward");
        goal1.setPoints(50);


        Goal goal2 = new Goal();
        goal2.setCategory("gymnastics");
        goal2.setTitle("Round off");
        goal2.setPoints(30);


        Goal goal3 = new Goal();
        goal3.setCategory("gymnastics");
        goal3.setTitle("Flic flac");
        goal3.setPoints(50);


        Goal goal4 = new Goal();
        goal4.setCategory("cheerleading");
        goal4.setTitle("Toss cupie");
        goal4.setPoints(80);


        Goal goal5 = new Goal();
        goal5.setCategory("cheerleading");
        goal5.setTitle("Walk-in-hands");
        goal5.setPoints(10);

        Goal goal6 = new Goal();
        goal6.setCategory("cheerleading");
        goal6.setTitle("Liberty");
        goal6.setPoints(50);

        List<Goal> goals = new ArrayList<>();
        goals.add(goal1);
        goals.add(goal2);
        goals.add(goal3);
        goals.add(goal4);
        goals.add(goal5);
        goals.add(goal6);

        return goals;
    }

    //    Required to enable cross-origin resource sharing
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/*");
                registry.addMapping("/api/*/*");
            }
        };
    }
}
