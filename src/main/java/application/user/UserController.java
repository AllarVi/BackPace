package application.user;

import application.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by allarviinamae on 15/04/16.
 * <p>
 * Rest api.
 */
@RestController
public class UserController extends BaseController {

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public ResponseEntity<PaceUser> getUser(@RequestParam(value = FACEBOOK_ID, required = false) String facebookId,
                                            @RequestParam(value = TOKEN) String token) {
        Connection<Facebook> connection = getFacebookConnection(token);

        if (connection == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (facebookId != null) {
            PaceUser currentPaceUser = getPaceUser(facebookId);
            currentPaceUser.setAccessToken(token);
            PaceUser updatedPaceUser = userRepository.save(currentPaceUser);
            return new ResponseEntity<>(updatedPaceUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(new PaceUser(), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    public ResponseEntity<PaceUser> saveNewPaceUser(@RequestBody String input) {
        try {
            PaceUser updatedPaceUser = mapFromJson(input, PaceUser.class);
            PaceUser currentPaceUser = getPaceUser(updatedPaceUser.getFacebookId());

            PaceUser resultUser;
            if (currentPaceUser != null) {
                updateCurrentPaceUser(currentPaceUser, updatedPaceUser);
                // Update current user
                resultUser = userRepository.save(currentPaceUser);
            } else {
                resultUser = userRepository.save(updatedPaceUser);
            }

            return new ResponseEntity<>(resultUser, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private void updateCurrentPaceUser(PaceUser currentPaceUser, PaceUser updatedPaceUser) {
        currentPaceUser.setName(updatedPaceUser.getName());
        currentPaceUser.setAuthResponse(updatedPaceUser.getAuthResponse());
        currentPaceUser.setAccessToken(updatedPaceUser.getAccessToken());
        currentPaceUser.setPicture(updatedPaceUser.getPicture());
    }

    @RequestMapping("/api/users")
    public Iterable<PaceUser> getAllUsers() {
        logger.info("Request made to /api/users");
        return userRepository.findAll();
    }

}

