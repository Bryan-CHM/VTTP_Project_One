package VTTP.Project.VTTP_Project_One.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import VTTP.Project.VTTP_Project_One.models.User;

@Controller
@RequestMapping("/savings")
public class SavingsController {

    @GetMapping()
    public ModelAndView getDashboard(HttpSession session){
        User user = (User)session.getAttribute("user");
        /**
         * Do some processing with user object
         */
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("dashboard");
        return mvc;
    }
}
