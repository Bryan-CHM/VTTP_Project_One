package VTTP.Project.VTTP_Project_One.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import VTTP.Project.VTTP_Project_One.models.User;
import VTTP.Project.VTTP_Project_One.repositories.LoginRepository;

@Controller
@RequestMapping()
public class LoginController {

    @Autowired
    private LoginRepository loginRepo;
    
    @GetMapping("/")
    public ModelAndView homePage(){
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("index");
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
        
        if(loginRepo.verifyUser(user)){
            mvc.setStatus(HttpStatus.OK);
            // mvc.addObject("user",user);
            session.setAttribute("user", user);
            mvc = new ModelAndView("redirect:/savings");
        }
        return mvc;
    }
}
