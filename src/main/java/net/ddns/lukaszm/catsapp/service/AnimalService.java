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

    private URI catUrl;
    private URI dogUrl;

    UserRepository userRepository;
    AnimalRepository animalRepository;

    public AnimalService(UserRepository userRepository, AnimalRepository animalRepository) throws URISyntaxException {
        this.catUrl = new URI("https://api.thecatapi.com/v1/images/search");
        this.dogUrl = new URI("https://api.thedogapi.com/v1/images/search");

        this.userRepository = userRepository;
        this.animalRepository = animalRepository;
    }

    public Animal getAnimal(AnimalType animalType) {
        RestTemplate template = new RestTemplate();

        ResponseEntity<String> entity;

        if(animalType == AnimalType.CAT)
            entity = template.getForEntity(catUrl, String.class);
        else
            entity = template.getForEntity(dogUrl, String.class);

        String response = entity.getBody().substring(1, entity.getBody().length() -1);

        JSONObject body = new JSONObject(response);

        return new Animal(null, null, body.getString("url"), animalType);
    }

    public void saveAnimal(UserDetails user, Animal animal) {
        animal.setUser(userRepository.findByUsername(user.getUsername()));
        animalRepository.save(animal);
    }

    public List<Animal> getAllAnimals(UserDetails user) {
        return animalRepository.findAllByUser(userRepository.findByUsername(user.getUsername()));
    }
}