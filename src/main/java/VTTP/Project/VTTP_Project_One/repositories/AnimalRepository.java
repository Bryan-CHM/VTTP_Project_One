package VTTP.Project.VTTP_Project_One.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import VTTP.Project.VTTP_Project_One.models.Animal;

@Repository
public class AnimalRepository {
    public static final String SQL_GET_FAVOURITE_ANIMALS = "select * from favourite_animals where user_id = ?";
    public static final String SQL_INSERT_FAVOURITE_ANIMAL = "insert into favourite_animals(user_id, name, animal_type, active_time, habitat, diet, location, lifespan, min_length, max_length, min_weight, max_weight, image_url) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_DELETE_FAVOURITE_ANIMAL = "delete from favourite_animals where user_id = ? and name = ?";
    public static final String SQL_GET_NUMBER_OF_FAVOURITES = "select count(*) as num from favourite_animals where name = ?";

    
    @Autowired
    JdbcTemplate template;

    public List<Animal> getFavouriteAnimals(Integer user_id){
        List<Animal> animals = new LinkedList<>();
        final SqlRowSet result = template.queryForRowSet(
            SQL_GET_FAVOURITE_ANIMALS, user_id);
    
        while(result.next()){
            Animal animal = new Animal();
            animal.setName(result.getString("name"));
            animal.setAnimal_type(result.getString("animal_type"));
            animal.setActive_time(result.getString("active_time"));
            animal.setHabitat(result.getString("habitat"));
            animal.setDiet(result.getString("diet"));
            animal.setLocation(result.getString("location"));
            animal.setMin_length(result.getDouble("min_length"));
            animal.setMax_length(result.getDouble("max_length"));
            animal.setMin_weight(result.getDouble("min_weight"));
            animal.setMax_weight(result.getDouble("max_weight"));
            animal.setLifespan(result.getInt("lifespan"));
            animal.setImage_url(result.getString("image_url"));
            animals.add(animal);  
        }
        return animals;   
    }

    public Boolean addFavouriteAnimal(Animal animal, Integer user_id){
        int count = template.update(SQL_INSERT_FAVOURITE_ANIMAL, 
        user_id, 
        animal.getName(),
        animal.getAnimal_type(),
        animal.getActive_time(),
        animal.getHabitat(),
        animal.getDiet(),
        animal.getLocation(),
        animal.getLifespan(),
        animal.getMin_length(),
        animal.getMax_length(),
        animal.getMin_weight(),
        animal.getMax_weight(),
        animal.getImage_url());

        return 1 == count;
    }

    public Boolean removeFavouriteAnimal(Animal animal, Integer user_id){
        int count = template.update(SQL_DELETE_FAVOURITE_ANIMAL, 
        user_id, animal.getName());
        return 1 == count;
    }

    public Integer getNumberOfFavourites(String name){
        Integer count = 0;
        final SqlRowSet result = template.queryForRowSet(SQL_GET_NUMBER_OF_FAVOURITES, 
         name);
         while(result.next()){
             count = result.getInt("num");
         }
        return count;
    }
    
}
