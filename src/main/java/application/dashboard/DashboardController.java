package application.dashboard;

import application.BaseController;
import application.model.TeamData;
import application.team.ShortTableRow;
import application.team.ShortTableRowRepository;
import application.team.Team;
import application.team.TeamRepository;
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
    TeamRepository teamRepository;

    @Autowired
    ShortTableRowRepository shortTableRowRepository;

    @RequestMapping(value = "/api/dashboard", method = RequestMethod.GET)
    public ResponseEntity<Object> getUserTeamView(@RequestParam(value = FACEBOOK_ID) String facebookId,
            @RequestParam(value = "teamView") String teamView, @RequestParam(value = TOKEN) String token) {

        Connection<Facebook> connection = getFacebookConnection(token);

        if (connection == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (teamView.equals("short")) {
            PaceUser paceUser = getPaceUser(facebookId);

            return new ResponseEntity<>(paceUser.getTeamList(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/dashboard/join_group", method = RequestMethod.GET)
    public ResponseEntity<Object> getGroups(@RequestParam(value = FACEBOOK_ID) String facebookId,
            @RequestParam(value = TOKEN) String token, @RequestParam(value = "groups") String groups) {

        Connection<Facebook> connection = getFacebookConnection(token);

        if (connection == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (groups.equals("all")) {
            List<Team> teams = teamRepository.findAll();

            PaceUser paceUser = userRepository.findByFacebookId(facebookId);
            List<Team> userTeams = paceUser.getTeamList();

            // Only show user teams he/she is not connected to yet
            teams.removeAll(userTeams);

            return new ResponseEntity<>(teams, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/dashboard/join_group", method = RequestMethod.POST)
    public ResponseEntity<Object> joinTeam(@RequestParam(value = FACEBOOK_ID) String facebookId,
            @RequestParam(value = TOKEN) String token, @RequestBody String teamDataInput) {

        Connection<Facebook> connection = getFacebookConnection(token);

        if (connection == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            TeamData teamData = mapFromJson(teamDataInput, TeamData.class);

            Team team = teamRepository.findOne(teamData.getTeamId());
            PaceUser paceUser = userRepository.findByFacebookId(facebookId);

            // Add user to selected team!
            addUserToTeam(team, paceUser);

            List<Team> teamList = paceUser.getTeamList();
            teamList.add(team);

            paceUser.setTeamList(teamList);

            userRepository.save(paceUser);

            return new ResponseEntity<>(team, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Mapping team data to object failed!");
        }

        return new ResponseEntity<>("failed", HttpStatus.OK);
    }

    private void addUserToTeam(Team team, PaceUser paceUser) {
        List<ShortTableRow> fullScoresTableList = team.getFullScoresTableList();

        ShortTableRow shortTableRow = new ShortTableRow(0, paceUser.getName(), "", 0, paceUser.getFacebookId());
        shortTableRowRepository.save(shortTableRow);

        fullScoresTableList.add(shortTableRow);

        team.setFullScoresTableList(fullScoresTableList);
    }
}
