package application.profile;

import application.BaseController;
import application.user.PaceUser;
import application.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by allarviinamae on 19/05/16.
 * <p>
 * Main controller for Profile tab.
 */
@RestController
public class ProfileController extends BaseController {

    @Autowired
    GoalRepository goalRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/api/profile/goal")
    public ResponseEntity<Object> getUserShortTeamView(@RequestParam(value = "facebookId") String facebookId,
                                                       @RequestParam(value = "token") String token, @RequestParam
                                                                   (value = "category", required = false) String
                                                                   category) {

        Connection<Facebook> connection = getFacebookConnection(token);

        if (connection == null) {
            return new ResponseEntity<>(new PaceUser().getShortTeamViewList(), HttpStatus.UNAUTHORIZED);
        }

        List<Goal> goalList;

        if (category != null) {
            goalList = goalRepository.findByCategory(category);
        } else {
            PaceUser paceUser = userRepository.findByFacebookId(facebookId);

            Goals goals = paceUser.getGoals();
            Map<String, ArrayList<Goal>> goalMap = goals.getGoalMap();

            return new ResponseEntity<>(goalMap, HttpStatus.OK);
        }

        return new ResponseEntity<>(goalList, HttpStatus.OK);
    }
}
