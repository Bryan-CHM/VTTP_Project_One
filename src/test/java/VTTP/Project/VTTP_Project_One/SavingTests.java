package VTTP.Project.VTTP_Project_One;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import VTTP.Project.VTTP_Project_One.models.User;
import VTTP.Project.VTTP_Project_One.repositories.LoginRepository;
import VTTP.Project.VTTP_Project_One.repositories.SavingsRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class SavingTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private LoginRepository loginRepo;

    @Autowired
    private SavingsRepository savingsRepo;

    private User fakeUserInfo(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        return user;
    }

    @BeforeEach
    public void createFakeUser(){
        loginRepo.createUser(fakeUserInfo());
    }
    
    @AfterEach
    public void deleteFakeUser(){
        loginRepo.deleteUser(fakeUserInfo());   
    }
    
    @Test
    public void validUserDashboardTest(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        RequestBuilder req = MockMvcRequestBuilders.get("/savings")
            .accept(MediaType.TEXT_HTML_VALUE)
            .session(session);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation for valid user dashboard", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            Integer statusCode = resp.getStatus();
            assertEquals(200,statusCode);
        } catch (Exception ex) {
            fail("cannot retrieve response for valid user dashboard", ex);
            return;
        }
    }

    @Test
    public void invalidUserDashboardTest(){
        User user = new User();
        user.setUsername("testabc");
        user.setPassword("testabc");
        
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        RequestBuilder req = MockMvcRequestBuilders.get("/savings")
            .accept(MediaType.TEXT_HTML_VALUE)
            .session(session);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation for invalid user dashboard", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            Integer statusCode = resp.getStatus();
            assertEquals(400,statusCode);
        } catch (Exception ex) {
            fail("cannot retrieve response for invalid user dashboard", ex);
            return;
        }
    }

    @Test
    public void validUserAddSavingsTest(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        RequestBuilder req = MockMvcRequestBuilders.post("/savings/add")
            .accept(MediaType.TEXT_HTML_VALUE)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("value", "500")
            .session(session);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation for adding savings", ex);
            return;
        }
        
        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            Integer statusCode = resp.getStatus();
            assertEquals(200,statusCode);
        } catch (Exception ex) {
            fail("cannot retrieve response for adding savings", ex);
            return;
        }
    }

    @Test
    public void invalidUserAddSavingsTest(){
        User user = new User();
        user.setUsername("testabc");
        user.setPassword("testabc");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        RequestBuilder req = MockMvcRequestBuilders.post("/savings/add")
            .accept(MediaType.TEXT_HTML_VALUE)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("value", "500")
            .session(session);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation for adding savings", ex);
            return;
        }
        
        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            Integer statusCode = resp.getStatus();
            assertEquals(400,statusCode);
        } catch (Exception ex) {
            fail("cannot retrieve response for adding savings", ex);
            return;
        }
    }

    @Test
    public void gettingSavingsFromValidUser(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");

        assertEquals(0, savingsRepo.getSavingsFromUser(user));
    }  

    @Test
    public void gettingSavingsFromInvalidUser(){
        User user = new User();
        user.setUsername("testabc");
        user.setPassword("testabc");

        assertEquals(-1, savingsRepo.getSavingsFromUser(user));
    }    

}
