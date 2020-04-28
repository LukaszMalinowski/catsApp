package net.ddns.kotki.kotkiapp.service;

import net.ddns.kotki.kotkiapp.model.AnimalType;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

@Service
public class AnimalService {

    private URI catUrl;
    private URI dogUrl;

    public AnimalService() throws URISyntaxException {
        this.catUrl = new URI("https://api.thecatapi.com/v1/images/search");
        this.dogUrl = new URI("https://api.thedogapi.com/v1/images/search");
    }

    public String getAnimal(AnimalType animalType) {
        RestTemplate template = new RestTemplate();

        ResponseEntity<String> entity;

        if(animalType == AnimalType.CAT)
            entity = template.getForEntity(catUrl, String.class);
        else
            entity = template.getForEntity(dogUrl, String.class);

        String response = entity.getBody().substring(1, entity.getBody().length() -1);

        JSONObject body = new JSONObject(response);

        return body.getString("url");
    }
}