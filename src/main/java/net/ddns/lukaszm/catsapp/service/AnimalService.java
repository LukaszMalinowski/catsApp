package net.ddns.lukaszm.catsapp.service;

import net.ddns.lukaszm.catsapp.dao.AnimalRepository;
import net.ddns.lukaszm.catsapp.dao.UserRepository;
import net.ddns.lukaszm.catsapp.entity.Animal;
import net.ddns.lukaszm.catsapp.entity.AnimalType;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.*;
import java.util.List;

@Service
public class AnimalService {

    private final String CAT_URL_STRING = "https://api.thecatapi.com/v1/images/search";
    private final String DOG_URL_STRING = "https://api.thedogapi.com/v1/images/search";

    private URI catUrl;
    private URI dogUrl;

    UserRepository userRepository;
    AnimalRepository animalRepository;

    public AnimalService(UserRepository userRepository, AnimalRepository animalRepository) throws URISyntaxException {
        this.catUrl = new URI(CAT_URL_STRING);
        this.dogUrl = new URI(DOG_URL_STRING);

        this.userRepository = userRepository;
        this.animalRepository = animalRepository;
    }

    public Animal getAnimal(AnimalType animalType) {

        ResponseEntity<String> entity;

        if(animalType == AnimalType.CAT)
            entity = getRandomCat();
        else
            entity = getRandomDog();

        JSONObject body = getJsonBody(entity.getBody());

        return new Animal(null, null, body.getString("url"), animalType);
    }

    public void saveAnimal(UserDetails user, Animal animal) {
        animal.setUser(userRepository.findByUsername(user.getUsername()));
        animalRepository.save(animal);
    }

    public List<Animal> getAllAnimals(UserDetails user) {
        return animalRepository.findAllByUser(userRepository.findByUsername(user.getUsername()));
    }

    private ResponseEntity<String> getRandomCat() {
        return new RestTemplate().getForEntity(catUrl, String.class);
    }

    private ResponseEntity<String> getRandomDog() {
        return new RestTemplate().getForEntity(dogUrl, String.class);
    }

    private JSONObject getJsonBody(String response) {
        parseResponse(response);
        return new JSONObject(response);
    }

    private String parseResponse(String response) {
        return response.substring(1, response.length() -1);
    }
}