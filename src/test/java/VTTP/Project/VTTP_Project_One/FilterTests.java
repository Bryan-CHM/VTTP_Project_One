package VTTP.Project.VTTP_Project_One;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
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

@SpringBootTest
@AutoConfigureMockMvc
public class FilterTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void filterRequestWithoutSession(){

        RequestBuilder req = MockMvcRequestBuilders.get("/protected/dashboard")
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
            String redirectedURL = resp.getRedirectedUrl();
            assertEquals("/",redirectedURL);
        } catch (Exception ex) {
            fail("cannot retrieve response for homepage", ex);
            return;
        }
    }

    @Test
    public void filterRequestWithEmptySession(){
        User user = new User();
        user.setUsername(null);
        user.setPassword(null);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        RequestBuilder req = MockMvcRequestBuilders.get("/protected/dashboard")
            .accept(MediaType.TEXT_HTML_VALUE)
            .session(session);

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
            String redirectedURL = resp.getRedirectedUrl();
            assertEquals("/",redirectedURL);
        } catch (Exception ex) {
            fail("cannot retrieve response for homepage", ex);
            return;
        }
    }
    
}
