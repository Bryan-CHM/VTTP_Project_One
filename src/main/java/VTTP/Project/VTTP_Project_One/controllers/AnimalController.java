package VTTP.Project.VTTP_Project_One.controllers;

import java.util.LinkedList;
import java.util.List;

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

import VTTP.Project.VTTP_Project_One.models.Animal;
import VTTP.Project.VTTP_Project_One.models.User;
import VTTP.Project.VTTP_Project_One.repositories.AnimalRepository;
import VTTP.Project.VTTP_Project_One.repositories.LoginRepository;
import VTTP.Project.VTTP_Project_One.services.AnimalService;

@Controller
@RequestMapping("/protected")
public class AnimalController {

    @Autowired
    private AnimalService animalSvc;

    @Autowired
    private LoginRepository loginRepo;

    @Autowired
    private AnimalRepository animalRepo;


    @GetMapping("/dashboard")
    public ModelAndView getDashboard(HttpSession session){
        User user = (User)session.getAttribute("user");
        ModelAndView mvc = new ModelAndView();

        List<Animal> favouriteAnimals = new LinkedList<>();
        favouriteAnimals = animalRepo.getFavouriteAnimals(loginRepo.checkIfUserExists(user));
        for(Animal a : favouriteAnimals){
            a.setLikes(animalRepo.getNumberOfFavourites(a.getName()));
        }
        mvc.addObject("user", user);
        mvc.addObject("favanimals", favouriteAnimals);
        mvc.setStatus(HttpStatus.OK);
        mvc.setViewName("dashboard");

        return mvc;
    }

    @PostMapping("/list")
    public ModelAndView postAnimalList(@RequestBody MultiValueMap<String, String> form, HttpSession session){
        User user = (User)session.getAttribute("user");
        ModelAndView mvc = new ModelAndView();

        Integer number = Integer.parseInt(form.getFirst(("num")));
        List<Animal> animals = animalSvc.getListOfAnimals(number);
        for(Animal a : animals){
            a.setLikes(animalRepo.getNumberOfFavourites(a.getName()));
        }
        mvc.addObject("user", user);
        mvc.addObject("number", number);
        mvc.addObject("animals", animals);
        mvc.setStatus(HttpStatus.OK);
        mvc.setViewName("results");

        return mvc;
    }

    @PostMapping("/add")
    public ModelAndView addFavouriteAnimal(@RequestBody MultiValueMap<String, String> form, HttpSession session){
        User user = (User)session.getAttribute("user");
        ModelAndView mvc = new ModelAndView();
        
        String name = form.getFirst("name");
        String animal_type = form.getFirst("animal_type");
        String active_time = form.getFirst("active_time");
        String habitat = form.getFirst("habitat");
        String diet = form.getFirst("diet");
        String location = form.getFirst("location");
        Integer lifespan = Integer.parseInt(form.getFirst("lifespan"));
        Double min_length = Double.parseDouble(form.getFirst("min_length"));
        Double max_length = Double.parseDouble(form.getFirst("max_length"));
        Double min_weight = Double.parseDouble(form.getFirst("min_weight"));
        Double max_weight = Double.parseDouble(form.getFirst("max_weight"));
        String image_url = form.getFirst("image_url");

        Animal animal = new Animal();
        animal.setName(name);
        animal.setAnimal_type(animal_type);
        animal.setActive_time(active_time);
        animal.setHabitat(habitat);
        animal.setDiet(diet);
        animal.setLocation(location);
        animal.setLifespan(lifespan);
        animal.setMin_length(min_length);
        animal.setMax_length(max_length);
        animal.setMin_weight(min_weight);
        animal.setMax_weight(max_weight);
        animal.setImage_url(image_url);

        animalRepo.addFavouriteAnimal(animal, loginRepo.checkIfUserExists(user));
        List<Animal> favouriteAnimals = new LinkedList<>();
        favouriteAnimals = animalRepo.getFavouriteAnimals(loginRepo.checkIfUserExists(user));
        for(Animal a : favouriteAnimals){
            a.setLikes(animalRepo.getNumberOfFavourites(a.getName()));
        }

        mvc.addObject("user", user);
        mvc.addObject("favanimals", favouriteAnimals);
        mvc.setStatus(HttpStatus.OK);
        mvc.setViewName("dashboard");

        return mvc;
    }

    @PostMapping("/remove")
    public ModelAndView removeFavouriteAnimal(@RequestBody MultiValueMap<String, String> form, HttpSession session){
        User user = (User)session.getAttribute("user");
        ModelAndView mvc = new ModelAndView();
        
        String name = form.getFirst("name");
        String animal_type = form.getFirst("animal_type");
        String active_time = form.getFirst("active_time");
        String habitat = form.getFirst("habitat");
        String diet = form.getFirst("diet");
        String location = form.getFirst("location");
        Integer lifespan = Integer.parseInt(form.getFirst("lifespan"));
        Double min_length = Double.parseDouble(form.getFirst("min_length"));
        Double max_length = Double.parseDouble(form.getFirst("max_length"));
        Double min_weight = Double.parseDouble(form.getFirst("min_weight"));
        Double max_weight = Double.parseDouble(form.getFirst("max_weight"));
        String image_url = form.getFirst("image_url");

        Animal animal = new Animal();
        animal.setName(name);
        animal.setAnimal_type(animal_type);
        animal.setActive_time(active_time);
        animal.setHabitat(habitat);
        animal.setDiet(diet);
        animal.setLocation(location);
        animal.setLifespan(lifespan);
        animal.setMin_length(min_length);
        animal.setMax_length(max_length);
        animal.setMin_weight(min_weight);
        animal.setMax_weight(max_weight);
        animal.setImage_url(image_url);

        animalRepo.removeFavouriteAnimal(animal, loginRepo.checkIfUserExists(user));
        List<Animal> favouriteAnimals = new LinkedList<>();
        favouriteAnimals = animalRepo.getFavouriteAnimals(loginRepo.checkIfUserExists(user));
        for(Animal a : favouriteAnimals){
            a.setLikes(animalRepo.getNumberOfFavourites(a.getName()));
        }

        mvc.addObject("user", user);
        mvc.addObject("favanimals", favouriteAnimals);
        mvc.setStatus(HttpStatus.OK);
        mvc.setViewName("dashboard");

        return mvc;
    }

    
}
