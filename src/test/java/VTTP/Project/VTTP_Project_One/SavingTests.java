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
public class SavingTests {

    @Autowired
    private MockMvc mvc;
    
    @Test
    public void homePageTest(){

        User user = new User();
        user.setUsername("username");
        user.setUsername("password");
        
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
            fail("cannot perform mvc invocation for dashboard", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            Integer statusCode = resp.getStatus();
            assertEquals(200,statusCode);
        } catch (Exception ex) {
            fail("cannot retrieve response for dashboard", ex);
            return;
        }
    }

}
