package VTTP.Project.VTTP_Project_One;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import VTTP.Project.VTTP_Project_One.models.User;
import VTTP.Project.VTTP_Project_One.repositories.LoginRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private LoginRepository loginRepo;

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
        User user = fakeUserInfo();
        template.update("delete from user where username = ?;", user.getUsername()); 
    }

    @Test
    public void homePageTest(){

        RequestBuilder req = MockMvcRequestBuilders.get("/")
            .accept(MediaType.TEXT_HTML_VALUE);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation for homepage", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            Integer statusCode = resp.getStatus();
            assertEquals(200,statusCode);
        } catch (Exception ex) {
            fail("cannot retrieve response for homepage", ex);
            return;
        }
    }

    @Test
    public void createUserPageTest(){

        RequestBuilder req = MockMvcRequestBuilders.get("/create")
            .accept(MediaType.TEXT_HTML_VALUE);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation for create user page", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            Integer statusCode = resp.getStatus();
            assertEquals(200,statusCode);
        } catch (Exception ex) {
            fail("cannot retrieve response for create user page", ex);
            return;
        }
    }

    @Test
    public void successfulCreateNewUserTest(){

        MockHttpSession session = new MockHttpSession();
        RequestBuilder req = MockMvcRequestBuilders.post("/create")
            .accept(MediaType.TEXT_HTML_VALUE)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("username", "testabc")
            .param("password", "testabc")
            .session(session);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation for successful create new user", ex);
            return;
            
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        User user = new User();
        try {
            user.setUsername("testabc");
            user.setPassword("testabc");
            assertTrue(loginRepo.checkIfUserExists(user) != -1);
        } catch (Exception ex) {
            fail("cannot retrieve response for successful create new user", ex);
            return;
        }
        // Clean up new user created
        template.update("delete from user where username = ?;", user.getUsername()); 
    }

    @Test
    public void unsuccessfulCreateNewUserTest(){

        MockHttpSession session = new MockHttpSession();
        RequestBuilder req = MockMvcRequestBuilders.post("/create")
            .accept(MediaType.TEXT_HTML_VALUE)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("username", "test")
            .param("password", "test")
            .session(session);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation for unsuccessful create new user", ex);
            return;
            
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            assertEquals(401,resp.getStatus());
        } catch (Exception ex) {
            fail("cannot retrieve response for unsuccessful create new user", ex);
            return;
        }
    }

    @Test
    public void successfulLoginTest(){

        MockHttpSession session = new MockHttpSession();
        RequestBuilder req = MockMvcRequestBuilders.post("/verify")
            .accept(MediaType.TEXT_HTML_VALUE)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("username", "test")
            .param("password", "test")
            .session(session);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation for successful login", ex);
            return;
            
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String redirectedURL = resp.getRedirectedUrl();
            assertEquals("/protected/dashboard",redirectedURL);
        } catch (Exception ex) {
            fail("cannot retrieve response for successful login", ex);
            return;
        }
    }


    @Test
    public void unsuccessfulLoginTest(){

        MockHttpSession session = new MockHttpSession();
        RequestBuilder req = MockMvcRequestBuilders.post("/verify")
            .accept(MediaType.TEXT_HTML_VALUE)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("username", "testabc")
            .param("password", "testabc")
            .session(session);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation for unsuccessful login", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            Integer statusCode = resp.getStatus();
            assertEquals(401,statusCode);
        } catch (Exception ex) {
            fail("cannot retrieve response for unsuccessful login", ex);
            return;
        }
    }

    @Test
    public void logoutTest(){

        MockHttpSession session = new MockHttpSession();
        RequestBuilder req = MockMvcRequestBuilders.get("/logout")
            .accept(MediaType.TEXT_HTML_VALUE)
            .session(session);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation for logout", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String redirectedURL = resp.getRedirectedUrl();
            assertEquals("/",redirectedURL);
        } catch (Exception ex) {
            fail("cannot retrieve response for logout", ex);
            return;
        }
    }

}
    

