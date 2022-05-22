package VTTP.Project.VTTP_Project_One.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import VTTP.Project.VTTP_Project_One.models.Animal;
import VTTP.Project.VTTP_Project_One.repositories.AnimalRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

@Service
public class AnimalService {
    private final String ANIMALS_API = "https://zoo-animal-api.herokuapp.com/animals/rand/";
    private final String ANIMAL_API = "https://zoo-animal-api.herokuapp.com/animals/rand/";

    @Autowired
    private AnimalRepository animalRepo;

    public List<Animal> getListOfAnimals(Integer number){
        List<Animal> animals = new LinkedList<>();

        String url = UriComponentsBuilder				
        .fromUriString(ANIMALS_API)
        .path(number.toString())
        .toUriString();

		RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.getForEntity(url, String.class); 
        InputStream is = new ByteArrayInputStream (resp.getBody().getBytes());
        JsonReader reader = Json.createReader(is);
        JsonArray data = reader.readArray();

        for(JsonValue val : data){
            JsonObject obj = val.asJsonObject();
            Animal animal = new Animal();
            animal.setName(obj.getString("name"));
            animal.setAnimal_type(obj.getString("animal_type"));
            animal.setActive_time(obj.getString("active_time"));
            animal.setHabitat(obj.getString("habitat"));
            animal.setDiet(obj.getString("diet"));
            animal.setLocation(obj.getString("geo_range"));
            animal.setMin_length(Double.parseDouble(obj.getString("length_min")));
            animal.setMax_length(Double.parseDouble(obj.getString("length_max")));
            animal.setMin_weight(Double.parseDouble(obj.getString("weight_min")));
            animal.setMax_weight(Double.parseDouble(obj.getString("weight_max")));
            animal.setLifespan(Integer.parseInt(obj.getString("lifespan")));
            animal.setImage_url(obj.getString("image_link"));
            animals.add(animal);  
        }    
        return animals;
    }
}
