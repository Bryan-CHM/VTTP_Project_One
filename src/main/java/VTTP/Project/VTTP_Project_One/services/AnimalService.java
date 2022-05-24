package VTTP.Project.VTTP_Project_One.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import VTTP.Project.VTTP_Project_One.models.Animal;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

@Service
public class AnimalService {
    private final String ANIMALS_API = "https://zoo-animal-api.herokuapp.com/animals/rand/";

    public List<Animal> getListOfAnimals(Integer number, List<Animal> favouriteAnimals){
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
        // Duplicate check and replacement
        for(int i =0;i<animals.size();i++){
            for(int j=0;j<favouriteAnimals.size();j++){
                if (animals.get(i).getName().equals(favouriteAnimals.get(j).getName())){
                    animals.remove(i);
                    i--;
                    animals.add(getAnimal());
                    break;
                }
            }
        }    
        return animals;
    }

    public Animal getAnimal(){
        String url = UriComponentsBuilder				
        .fromUriString(ANIMALS_API)
        .toUriString();
        
		RestTemplate template = new RestTemplate();
        
        ResponseEntity<String> resp = template.getForEntity(url, String.class); 
        InputStream is = new ByteArrayInputStream (resp.getBody().getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject data = reader.readObject();
        
        Animal animal = new Animal();
        animal.setName(data.getString("name"));
        animal.setAnimal_type(data.getString("animal_type"));
        animal.setActive_time(data.getString("active_time"));
        animal.setHabitat(data.getString("habitat"));
        animal.setDiet(data.getString("diet"));
        animal.setLocation(data.getString("geo_range"));
        animal.setMin_length(Double.parseDouble(data.getString("length_min")));
        animal.setMax_length(Double.parseDouble(data.getString("length_max")));
        animal.setMin_weight(Double.parseDouble(data.getString("weight_min")));
        animal.setMax_weight(Double.parseDouble(data.getString("weight_max")));
        animal.setLifespan(Integer.parseInt(data.getString("lifespan")));
        animal.setImage_url(data.getString("image_link")); 
         
        return animal;
    }
}
