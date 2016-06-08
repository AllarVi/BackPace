package application;

import application.model.TeamData;
import com.fasterxml.jackson.core.JsonParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.MockitoAnnotations;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Transactional
public class BaseControllerTest extends AbstractControllerTest {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testGetPaceUser() throws Exception {

    }

    @Test
    public void testGetFacebookConnectionFailed() throws Exception {
        BaseController baseController = new BaseController();

        Connection<Facebook> facebookConnection = baseController.getFacebookConnection("notValidToken");

        Assert.assertNull("failure - expected facebookConnection should be null", facebookConnection);
    }

    @Test
    public void testMapFromJson() throws Exception {
        BaseController baseController = new BaseController();

        long teamDataId = 3L;
        TeamData teamData = baseController
                .mapFromJson("{" + "  \"teamId\": \" " + teamDataId + " \"" + "}", TeamData.class);

        Assert.assertEquals("failure - expected same teamId as given", teamDataId, teamData.getTeamId());
    }
}