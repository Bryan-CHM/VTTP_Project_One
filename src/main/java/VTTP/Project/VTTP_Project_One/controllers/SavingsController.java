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
import VTTP.Project.VTTP_Project_One.repositories.SavingsRepository;

@Controller
@RequestMapping("/savings")
public class SavingsController {

    @Autowired
    private SavingsRepository savingsRepo;

    @GetMapping()
    public ModelAndView getDashboard(HttpSession session){
        User user = (User)session.getAttribute("user");
        ModelAndView mvc = new ModelAndView();
        mvc.setStatus(HttpStatus.BAD_REQUEST);
        mvc.setViewName("error");

        Integer savings = savingsRepo.getSavingsFromUser(user);

        if (savings != -1){
            user.setSavings(savings);
            mvc.addObject("user", user);
            mvc.setStatus(HttpStatus.OK);
            mvc.setViewName("dashboard");
        }

        return mvc;
    }

    @PostMapping("/add")
    public ModelAndView addSavings(@RequestBody MultiValueMap<String, String> form, HttpSession session){
        User user = (User)session.getAttribute("user");

        ModelAndView mvc = new ModelAndView();
        mvc.setStatus(HttpStatus.BAD_REQUEST);
        mvc.setViewName("error");
        
        Integer value = Integer.parseInt(form.getFirst("value"));
        Boolean success = savingsRepo.addSavings(user, value);
        Integer newSavings = 0;
        if(success){
            newSavings = savingsRepo.getSavingsFromUser(user);        
            if( newSavings != -1){
                user.setSavings(newSavings);
                mvc.addObject("user", user);
                mvc.setStatus(HttpStatus.OK);
                mvc.setViewName("dashboard");
            }
        }
        return mvc;
    }
}
