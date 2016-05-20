package application.dashboard;

import application.BaseController;
import application.model.TeamData;
import application.team.ShortTableRow;
import application.team.ShortTableRowRepository;
import application.team.ShortTeamView;
import application.team.ShortTeamViewRepository;
import application.user.PaceUser;
import application.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by allarviinamae on 16/05/16.
 * <p>
 * Main controller for Dashboard tab.
 */
@RestController
public class DashboardController extends BaseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShortTeamViewRepository shortTeamViewRepository;

    @Autowired
    ShortTableRowRepository shortTableRowRepository;

    @RequestMapping(value = "/api/dashboard")
    public ResponseEntity<Object> getUserShortTeamView(@RequestParam(value = "facebookId") String facebookId,
                                                       @RequestParam(value = "teamView") String teamView,
                                                       @RequestParam(value = "token") String token) {

        Connection<Facebook> connection = getFacebookConnection(token);

        if (connection == null) {
            return new ResponseEntity<>(new PaceUser().getShortTeamViewList(), HttpStatus.UNAUTHORIZED);
        }

        if (teamView.equals("short")) {
            PaceUser paceUser = getPaceUser(facebookId);

            return new ResponseEntity<>(paceUser.getShortTeamViewList(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/dashboard/join_group")
    public ResponseEntity<Object> getGroups(@RequestParam(value = "facebookId") String facebookId, @RequestParam
            (value = "token") String token, @RequestParam(value = "groups") String groups) {

        Connection<Facebook> connection = getFacebookConnection(token);

        if (connection == null) {
            return new ResponseEntity<>(new PaceUser().getShortTeamViewList(), HttpStatus.UNAUTHORIZED);
        }

        if (groups.equals("all")) {
            List<ShortTeamView> teams = shortTeamViewRepository.findAll();

            PaceUser paceUser = userRepository.findByFacebookId(facebookId);
            List<ShortTeamView> userTeams = paceUser.getShortTeamViewList();

//            Only show user teams he/she is not connected to yet
            teams.removeAll(userTeams);

            return new ResponseEntity<>(teams, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/dashboard/join_group", method = RequestMethod.POST)
    public ResponseEntity<Object> joinTeam(@RequestParam(value = "facebookId") String facebookId, @RequestParam(value
            = "token") String token, @RequestBody String groupData) {

        Connection<Facebook> connection = getFacebookConnection(token);

        if (connection == null) {
            return new ResponseEntity<>(new PaceUser().getShortTeamViewList(), HttpStatus.UNAUTHORIZED);
        }

        try {
            TeamData teamData = mapFromJson(groupData, TeamData.class);

            ShortTeamView shortTeamView = shortTeamViewRepository.findOne(teamData.getTeamId());

            PaceUser paceUser = userRepository.findByFacebookId(facebookId);

//             Add user to selected team!
            addUserToTeam(shortTeamView, paceUser);

            List<ShortTeamView> shortTeamViews = paceUser.getShortTeamViewList();

            shortTeamViews.add(shortTeamView);

            paceUser.setShortTeamViewList(shortTeamViews);

            userRepository.save(paceUser);

            return new ResponseEntity<>(shortTeamView, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Mapping team data to object failed!");
        }


        return new ResponseEntity<>("failed", HttpStatus.OK);
    }

    private void addUserToTeam(ShortTeamView shortTeamView, PaceUser paceUser) {
        List<ShortTableRow> shortTableRowList = shortTeamView.getShortTableRowMap();

        ShortTableRow shortTableRow = new ShortTableRow(0, paceUser.getName(), "", 0);
        shortTableRowRepository.save(shortTableRow);

        shortTableRowList.add(shortTableRow);

        shortTeamView.setShortTableRowMap(shortTableRowList);

//        shortTeamViewRepository.save(shortTeamView);
    }
}
