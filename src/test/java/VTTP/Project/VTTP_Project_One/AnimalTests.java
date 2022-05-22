package VTTP.Project.VTTP_Project_One;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import VTTP.Project.VTTP_Project_One.models.Animal;
import VTTP.Project.VTTP_Project_One.models.User;
import VTTP.Project.VTTP_Project_One.repositories.AnimalRepository;
import VTTP.Project.VTTP_Project_One.repositories.LoginRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimalTests {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private LoginRepository loginRepo;

    @Autowired
    private AnimalRepository animalRepo;

    private User fakeUserInfo(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        return user;
    }

    private Animal fakeAnimalInfo(){
        Animal animal = new Animal();
        animal.setName("testanimal");
        animal.setAnimal_type("type");
        animal.setActive_time("time");
        animal.setHabitat("habitat");
        animal.setDiet("diet");
        animal.setLocation("location");
        animal.setLifespan(10);
        animal.setMin_length(3.5);
        animal.setMax_length(5.0);
        animal.setMin_weight(2.5);
        animal.setMax_weight(4.0);
        animal.setImage_url("image_url");
        return animal;
    }

    private Animal fakeAnimalInfoTwo(){
        Animal animal = new Animal();
        animal.setName("testanimal2");
        animal.setAnimal_type("type2");
        animal.setActive_time("time2");
        animal.setHabitat("habitat2");
        animal.setDiet("diet2");
        animal.setLocation("location2");
        animal.setLifespan(102);
        animal.setMin_length(3.52);
        animal.setMax_length(5.02);
        animal.setMin_weight(2.52);
        animal.setMax_weight(4.02);
        animal.setImage_url("image_url2");
        return animal;
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
    public void validUserDashboardTest(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        Integer userId = loginRepo.checkIfUserExists(user);
        Animal animal = fakeAnimalInfo();
        // Add Fake Favourite Animal
        animalRepo.addFavouriteAnimal(animal, userId);
        
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
        // Clean up fake favourite animal
        animalRepo.removeFavouriteAnimal(animal, userId);
    }

    @Test
    public void animalListTest(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        RequestBuilder req = MockMvcRequestBuilders.post("/protected/list")
            .accept(MediaType.TEXT_HTML_VALUE)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("num", "10")
            .session(session);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation for displaying animal list", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            Integer statusCode = resp.getStatus();
            assertEquals(200,statusCode);
        } catch (Exception ex) {
            fail("cannot retrieve response for displaying animal list", ex);
            return;
        }
    }

    @Test
    public void addFavouriteAnimalTest(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        Integer userId = loginRepo.checkIfUserExists(user);

        Animal animal = fakeAnimalInfo();
        
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        RequestBuilder req = MockMvcRequestBuilders.post("/protected/add")
            .accept(MediaType.TEXT_HTML_VALUE)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("name", "testanimal")
            .param("animal_type", "type")
            .param("active_time", "time")
            .param("habitat", "habitat")
            .param("diet", "diet")
            .param("location", "location")
            .param("lifespan", "10")
            .param("min_length", "3.5")
            .param("max_length", "5.0")
            .param("min_weight", "2.5")
            .param("max_weight", "4")
            .param("image_url", "image_url")
            .session(session);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation for adding favourite animal", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            Integer statusCode = resp.getStatus();
            assertEquals(200,statusCode);
        } catch (Exception ex) {
            fail("cannot retrieve response for adding favourite animal", ex);
            return;
        }

        animalRepo.removeFavouriteAnimal(animal, userId);
    }

    @Test
    public void removeExistingFavouriteAnimalTest(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        Integer userId = loginRepo.checkIfUserExists(user);

        // Add fake favourite animals
        Animal animal = fakeAnimalInfo();
        Animal animalTwo = fakeAnimalInfoTwo();
        animalRepo.addFavouriteAnimal(animal, userId);
        animalRepo.addFavouriteAnimal(animalTwo, userId);
        
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        RequestBuilder req = MockMvcRequestBuilders.post("/protected/remove")
            .accept(MediaType.TEXT_HTML_VALUE)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("name", "testanimal")
            .param("animal_type", "type")
            .param("active_time", "time")
            .param("habitat", "habitat")
            .param("diet", "diet")
            .param("location", "location")
            .param("lifespan", "10")
            .param("min_length", "3.5")
            .param("max_length", "5.0")
            .param("min_weight", "2.5")
            .param("max_weight", "4")
            .param("image_url", "image_url")
            .session(session);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation for removing favourite animal", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            Integer statusCode = resp.getStatus();
            assertEquals(200,statusCode);
        } catch (Exception ex) {
            fail("cannot retrieve response for removing favourite animal", ex);
            return;
        }
        // Clean up fake favourite animal
        animalRepo.removeFavouriteAnimal(animalTwo, userId);
    }

    @Test
    public void removeNonExistingFavouriteAnimalTest(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        RequestBuilder req = MockMvcRequestBuilders.post("/protected/remove")
            .accept(MediaType.TEXT_HTML_VALUE)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("name", "testanimal")
            .param("animal_type", "type")
            .param("active_time", "time")
            .param("habitat", "habitat")
            .param("diet", "diet")
            .param("location", "location")
            .param("lifespan", "10")
            .param("min_length", "3.5")
            .param("max_length", "5.0")
            .param("min_weight", "2.5")
            .param("max_weight", "4")
            .param("image_url", "image_url")
            .session(session);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation for removing non-existing favourite animal", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            Integer statusCode = resp.getStatus();
            assertEquals(200,statusCode);
        } catch (Exception ex) {
            fail("cannot retrieve response for removing non-existing favourite animal", ex);
            return;
        }
    }

    @Test
    public void viewValidUserFavouritesTest(){

        RequestBuilder req = MockMvcRequestBuilders.get("/user/test")
        .accept(MediaType.TEXT_HTML_VALUE);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation for viewing user's favourites", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            Integer statusCode = resp.getStatus();
            assertEquals(200,statusCode);
        } catch (Exception ex) {
            fail("cannot retrieve response for viewing user's favourites", ex);
            return;
        }
    }

    @Test
    public void viewInvalidUserFavouritesTest(){

        RequestBuilder req = MockMvcRequestBuilders.get("/user/testabc")
        .accept(MediaType.TEXT_HTML_VALUE);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation for viewing user's favourites", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            Integer statusCode = resp.getStatus();
            assertEquals(401,statusCode);
        } catch (Exception ex) {
            fail("cannot retrieve response for viewing user's favourites", ex);
            return;
        }
    }
}
