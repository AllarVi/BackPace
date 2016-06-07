package application;

import application.user.PaceUser;
import application.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Created by allarviinamae on 03/05/16.
 * <p>
 * Base controller class for all controllers.
 */
public class BaseController {

    private static final String clientId = "269321573400083";              // clientId from facebook app
    private static final String clientSecret = "18f4b0c8f3bf178842f92f73b2fc12a4";     // clientSecret

    protected static final String FACEBOOK_ID = "facebookId";
    protected static final String TOKEN = "token";

    @Autowired
    UserRepository userRepository;

    protected PaceUser getPaceUser(@RequestParam(value = FACEBOOK_ID) String facebookId) {
        return userRepository.findByFacebookId(facebookId);
    }

    protected Connection<Facebook> getFacebookConnection(@RequestParam(TOKEN) String token) {
        FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory(clientId, clientSecret);

        AccessGrant accessGrant = new AccessGrant(token);

        try {
            return facebookConnectionFactory.createConnection(accessGrant);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected <T> T mapFromJson(String json, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }
}
