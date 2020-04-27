package net.ddns.kotki.kotkiapp;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class DogService {

    private URL dogUrl;

    public DogService() throws MalformedURLException{
        this.dogUrl = new URL("https://api.thedogapi.com/v1/images/search");
    }

    public String getDog() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) dogUrl.openConnection();
        connection.setRequestMethod("GET");

        int code = connection.getResponseCode();

        StringBuilder stringBuilder = new StringBuilder();

        if(code == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = "";

            while ((line = in.readLine()) != null) {
                stringBuilder.append(line);
            }

            in.close();
        }

        String json = stringBuilder.toString();
        json = json.substring(1, json.length()-1);
        JSONObject jsonObject = new JSONObject(json);

        return jsonObject.getString("url");
    }
}