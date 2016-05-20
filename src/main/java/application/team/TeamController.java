package application.team;

import application.BaseController;
import application.user.PaceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by allarviinamae on 20/05/16.
 * <p>
 * Main controller for Team view.
 */
@RestController
public class TeamController extends BaseController {

    @Autowired
    TeamRepository teamRepository;

    @RequestMapping(value = "/api/team")
    public ResponseEntity<Object> getTeamData(@RequestParam(value = "facebookId") String facebookId, @RequestParam
            (value = "token") String token, @RequestParam(value = "teamId") String teamId) {

        Connection<Facebook> connection = getFacebookConnection(token);

        if (connection == null) {
            return new ResponseEntity<>(new PaceUser().getShortTeamViewList(), HttpStatus.UNAUTHORIZED);
        }

        Team team = teamRepository.findOne(Long.valueOf(teamId));

        return new ResponseEntity<>(team.getFullScoresTableList(), HttpStatus.OK);
    }
}
