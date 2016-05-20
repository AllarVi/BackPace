package application.profile;

import application.AbstractControllerTest;
import application.user.UserController;
import application.user.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by allarviinamae on 19/05/16.
 * <p>
 * ProfileController mock tests.
 */
@Transactional
public class ProfileControllerMocksTest extends AbstractControllerTest {

    public static final String USER_ID = "1273703759309879";
    public static final String USER_TOKEN =
            "EAAD08lC2fhMBAIWW7X6j85X0s8IREqlmaXlV47g5NZBOsk22L616ooPDtmUCD7Rup7vVkmKAFP5k5y5zNbf0ZBZB0XGU2fbvaUx7uUxLfY3lStaOyCoo3SiVn9kNGTW5NIon6JC2BNspoLex6NfCBZBkgEZCAyfn0JbICsgpuLu0FTO2zcEsiULORo2nnZBLMZD";
    @Mock
    private UserRepository userRepository;

    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private UserController userController;

    @InjectMocks
    private ProfileController profileController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        setUp(userController);
        setUp(profileController);
    }

    @Test
    public void testGetGoalsByCategory() throws Exception {

        // Create some test data
        List<Goal> initGoals = getInitGoals();

        String category = "gymnastics";

        // Stub the UserRepository.findAll method return value
        when(goalRepository.findByCategory(category)).thenReturn(initGoals);

        // Perform the behavior being tested
        String uri = "/api/profile/goal?facebookId=" + USER_ID + "&token=" + USER_TOKEN + "&category=" + category;

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the GreetingService.findAll method was invoked once
        verify(goalRepository, times(1)).findByCategory(category);

        // Perform standard JUnit assertions on the response
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);
    }

    @Test
    public void testGetGoals() throws Exception {

        // Create some test data
        List<Goal> initGoals = getInitGoals();

        // Stub the UserRepository.findAll method return value
        when(goalRepository.findAll()).thenReturn(initGoals);

        // Perform the behavior being tested
        String uri = "/api/profile/goal?facebookId=" + USER_ID + "&token=" + USER_TOKEN;

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the GreetingService.findAll method was invoked once
        verify(goalRepository, times(1)).findAll();

        // Perform standard JUnit assertions on the response
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);
    }

    private List<Goal> getInitGoals() {

        Goal goal1 = new Goal();
        goal1.setCategory("gymnastics");
        goal1.setTitle("Salto backward");


        Goal goal2 = new Goal();
        goal2.setCategory("gymnastics");
        goal2.setTitle("Round off");


        Goal goal3 = new Goal();
        goal3.setCategory("gymnastics");
        goal3.setTitle("Flic flac");


        Goal goal4 = new Goal();
        goal4.setCategory("cheerleading");
        goal4.setTitle("Toss cupie");


        Goal goal5 = new Goal();
        goal5.setCategory("cheerleading");
        goal5.setTitle("Walk-in-hands");

        List<Goal> goals = new ArrayList<>();
        goals.add(goal1);
        goals.add(goal2);
        goals.add(goal3);
        goals.add(goal4);
        goals.add(goal5);

        return goals;
    }
}
