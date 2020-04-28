package net.ddns.kotki.kotkiapp.service;

import net.ddns.kotki.kotkiapp.dao.AnimalRepository;
import net.ddns.kotki.kotkiapp.dao.UserRepository;
import net.ddns.kotki.kotkiapp.entity.Animal;
import net.ddns.kotki.kotkiapp.entity.User;
import net.ddns.kotki.kotkiapp.entity.AnimalType;
import org.json.JSONObject;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.*;

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

    @EventListener (ApplicationReadyEvent.class)
    public void start() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userRepository.save(new User(1L, "Jan", encoder.encode("Jan123"), "USER"));
        animalRepository.save(new Animal(1L, userRepository.findById(1L).get(), "https://cdn2.thecatapi.com/images/2np.jpg", AnimalType.CAT));
    }
}