package VTTP.Project.VTTP_Project_One.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import VTTP.Project.VTTP_Project_One.models.Animal;
import VTTP.Project.VTTP_Project_One.models.User;
import VTTP.Project.VTTP_Project_One.repositories.AnimalRepository;
import VTTP.Project.VTTP_Project_One.repositories.LoginRepository;

@Controller
@RequestMapping()
public class LoginController {

    @Autowired
    private LoginRepository loginRepo;

    @Autowired
    private AnimalRepository animalRepo;
    
    @GetMapping("/")
    public ModelAndView homePage(){
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("index");
        return mvc;
    }
    
    @GetMapping("/create")
    public ModelAndView createUserPage(){
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("create");
        return mvc;
    }
    
    @PostMapping("/create")
    public ModelAndView createNewUser(@RequestBody MultiValueMap<String, String> form, HttpSession session){
        ModelAndView mvc = new ModelAndView();
        String username = form.getFirst("username");
        String password = form.getFirst("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        if(loginRepo.checkIfUserExists(user) != -1){
            mvc.setStatus(HttpStatus.UNAUTHORIZED);
            mvc.setViewName("invalid");
            mvc.addObject("errormessage", "0");
        }
        else{
            loginRepo.createUser(user);
            session.setAttribute("user", user);
            mvc = new ModelAndView("redirect:/protected/dashboard");
        }

        return mvc;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session){
        session.invalidate();
        ModelAndView mvc = new ModelAndView();
        mvc = new ModelAndView("redirect:/");
        return mvc;
    }

    @PostMapping("/verify")
    public ModelAndView postLogin(@RequestBody MultiValueMap<String, String> form, HttpSession session){
        String username = form.getFirst("username");
        String password = form.getFirst("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        ModelAndView mvc = new ModelAndView();
        mvc.setStatus(HttpStatus.UNAUTHORIZED);
        mvc.setViewName("invalid");
        mvc.addObject("errormessage", "1");
        
        if(loginRepo.verifyUser(user)){
            session.setAttribute("user", user);
            mvc = new ModelAndView("redirect:/protected/dashboard");
        }
        return mvc;
    }

    @GetMapping("/user/{name}")
    public ModelAndView viewUserFavourites(@PathVariable String name){
        ModelAndView mvc = new ModelAndView();
        User user = new User();
        user.setUsername(name);
        Integer userId = loginRepo.checkIfUserExists(user);
        mvc.addObject("name", name);
        if(userId != -1){
            List<Animal> animals = animalRepo.getFavouriteAnimals(userId);
            mvc.addObject("animals", animals);
            mvc.setViewName("publicfavourites");
            mvc.setStatus(HttpStatus.OK);
        }
        else{
            mvc.addObject("errormessage", "2");
            mvc.setViewName("invalid");
            mvc.setStatus(HttpStatus.UNAUTHORIZED);
        }
        return mvc;
    }
}
