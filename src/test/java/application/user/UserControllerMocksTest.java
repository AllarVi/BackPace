package application.user;

import application.AbstractControllerTest;
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

@Transactional
public class UserControllerMocksTest extends AbstractControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        setUp(userController);
    }

    @Test
    public void testGetPaceUsers() throws Exception {

        // Create some test data
        List<PaceUser> entityListStubData = getEntityListStubData();

        // Stub the UserRepository.findAll method return value
        when(userRepository.findAll()).thenReturn(entityListStubData);

        // Perform the behavior being tested
        String uri = "/api/users";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the GreetingService.findAll method was invoked once
        verify(userRepository, times(1)).findAll();

        // Perform standard JUnit assertions on the response
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);
    }

    private List<PaceUser> getEntityListStubData() {

        PaceUser paceUserAuthSuccess = getPaceUserAuthSuccess();

        ArrayList<PaceUser> entityList = new ArrayList<>();
        entityList.add(paceUserAuthSuccess);

        return entityList;
    }

    private PaceUser getPaceUserAuthSuccess() {
        PaceUser paceUserAuthSuccess = new PaceUser();
        paceUserAuthSuccess.setName("William Wallace");
        paceUserAuthSuccess.setFacebookId("1273703759309879");
        paceUserAuthSuccess.setAuthResponse("success");
        paceUserAuthSuccess.setAccessToken(
                "EAAD08lC2fhMBAJndhmi8SZCDoFrZAPKBjVZAjYdOjdx9n39StxZAtBtuLKUVEzq6HHTVHZC3B6ZCGymj2iQbLj4PIPNsbkgA7mZAxoFKejCFIuegh6da8keBarMj5yMFCQsS7EiqeZB4oY2nycUl4ZAhx6iGZAPCCNevhdDWhTM5uK0FJspaSNSm8sEeDODaM01SAZD");
        return paceUserAuthSuccess;
    }

    private PaceUser getCurrentEntity() {
        PaceUser paceUserAuthSuccess = new PaceUser();
        paceUserAuthSuccess.setName("William Wallace Jr.");
        paceUserAuthSuccess.setFacebookId("1273703759309879");
        paceUserAuthSuccess.setAuthResponse("success");
        paceUserAuthSuccess.setAccessToken(
                "EAAD08lC2fhMBAJndhmi8SZCDoFrZAPKBjVZAjYdOjdx9n39StxZAtBtuLKUVEzq6HHTVHZC3B6ZCGymj2iQbLj4PIPNsbkgA7mZAxoFKejCFIuegh6da8keBarMj5yMFCQsS7EiqeZB4oY2nycUl4ZAhx6iGZAPCCNevhdDWhTM5uK0FJspaSNSm8sEeDODaM01SAZD");
        return paceUserAuthSuccess;
    }

    //    @Test
    //    public void testGetGreetingNotFound() throws Exception {
    //
    //        // Create some test data
    //        Long id = Long.MAX_VALUE;
    //
    //        // Stub the GreetingService.findOne method return value
    //        when(greetingService.findOne(id)).thenReturn(null);
    //
    //        // Perform the behavior being tested
    //        String uri = "/api/greetings/{id}";
    //
    //        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
    //                .accept(MediaType.APPLICATION_JSON)).andReturn();
    //
    //        // Extract the response status and body
    //        String content = result.getResponse().getContentAsString();
    //        int status = result.getResponse().getStatus();
    //
    //        // Verify the GreetingService.findOne method was invoked once
    //        verify(greetingService, times(1)).findOne(id);
    //
    //        // Perform standard JUnit assertions on the test results
    //        Assert.assertEquals("failure - expected HTTP status 404", 404, status);
    //        Assert.assertTrue("failure - expected HTTP response body to be empty",
    //                content.trim().length() == 0);
    //
    //    }

    @Test
    public void testCreatePaceUser() throws Exception {

        // Create some test data
        PaceUser entity = getPaceUserAuthSuccess();

        // Stub the UserRepository.save method return value
        when(userRepository.save(any(PaceUser.class))).thenReturn(entity);

        // Perform the behavior being tested
        String uri = "/api/user";
        String inputJson = super.mapToJson(entity);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the userRepository.save method was invoked once
        verify(userRepository, times(1)).save(any(PaceUser.class));

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 201", 201, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

        PaceUser createdEntity = super.mapFromJson(content, PaceUser.class);

        Assert.assertNotNull("failure - expected entity not null", createdEntity);
        Assert.assertNotNull("failure - expected id attribute not null", createdEntity.getId());
        Assert.assertEquals("failure - expected text attribute match", entity.getName(), createdEntity.getName());
    }

    @Test
    public void testSaveNewUser() throws Exception {

        // Create some test data
        PaceUser entity = getPaceUserAuthSuccess();
        Long id = 1L;

        // Stub the UserRepository.save method return value
        when(userRepository.save(any(PaceUser.class))).thenReturn(entity);

        // Perform the behavior being tested
        String uri = "/api/user";
        String inputJson = super.mapToJson(entity);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri, id).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the userRepository.save method was invoked once
        verify(userRepository, times(1)).save(any(PaceUser.class));

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 201", 201, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

        PaceUser resultEntity = super.mapFromJson(content, PaceUser.class);

        Assert.assertNotNull("failure - expected entity not null", resultEntity);
        Assert.assertEquals("failure - expected id attribute unchanged", entity.getId(), resultEntity.getId());
        Assert.assertEquals("failure - expected text attribute match", entity.getAuthResponse(),
                resultEntity.getAuthResponse());
    }

    @Test
    public void testUpdateUser() throws Exception {

        // Create some test data
        PaceUser entity = getPaceUserAuthSuccess();
        PaceUser currentEntity = getCurrentEntity();
        Long id = 1L;

        // Stub the UserRepository.save method return value
        when(userRepository.save(any(PaceUser.class))).thenReturn(entity);
        when(userRepository.findByFacebookId(entity.getFacebookId())).thenReturn(currentEntity);

        // Perform the behavior being tested
        String uri = "/api/user";
        String inputJson = super.mapToJson(entity);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri, id).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the userRepository.save method was invoked once
        verify(userRepository, times(1)).save(any(PaceUser.class));
        verify(userRepository, times(1)).findByFacebookId(entity.getFacebookId());

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 201", 201, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

        PaceUser resultEntity = super.mapFromJson(content, PaceUser.class);

        Assert.assertNotNull("failure - expected entity not null", resultEntity);
        Assert.assertEquals("failure - expected id attribute unchanged", entity.getId(), resultEntity.getId());
        Assert.assertEquals("failure - expected text attribute match", entity.getName(), resultEntity.getName());
    }

    @Test
    public void testGetUserFacebookId() throws Exception {

        // Create some test data
        PaceUser entity = getPaceUserAuthSuccess();

        // Stub the UserRepository.save method return value
        when(userRepository.findByFacebookId(entity.getFacebookId())).thenReturn(entity);

        // Perform the behavior being tested
        String uri = "/api/user?facebookId=" + entity.getFacebookId();

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the userRepository.findByFacebookId method was invoked once
        verify(userRepository, times(1)).findByFacebookId(entity.getFacebookId());

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

        PaceUser responsePaceUser = super.mapFromJson(content, PaceUser.class);

        Assert.assertNotNull("failure - expected entity not null", responsePaceUser);
        Assert.assertEquals("failure - expected paceUser with facebookId", entity.getFacebookId(),
                responsePaceUser.getFacebookId());
    }

    @Test
    public void testCreateUserBadRequest() throws Exception {
        // Perform the behavior being tested
        String uri = "/api/user";
        Long id = 1L;

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri, id).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(super.mapToJson(""))).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the userRepository.save method was not invoked
        verify(userRepository, times(0)).save(any(PaceUser.class));

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 400", 400, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() == 0);
    }

}
