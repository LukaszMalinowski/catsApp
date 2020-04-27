package net.ddns.kotki.kotkiapp;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class CatService {

    private URL catUrl;

    public CatService() throws MalformedURLException{
        this.catUrl = new URL("https://api.thecatapi.com/v1/images/search");
    }

    public String getCat() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) catUrl.openConnection();
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

        return parseToUrl(stringBuilder.toString());
    }

    public String parseToUrl(String json) {
        String[] split = json.split(",");
        String url = split[2];

        url = url.substring(url.indexOf(":") + 2, url.length()-1);
        return url;
    }
}

//    Socket socket = new Socket(catUrl.getHost(), 80);
//    String request = "GET / HTTP/1.0\r\n\r\n";
//
//    OutputStream out = socket.getOutputStream();
//        out.write(request.getBytes());
//                out.flush();
//
//                StringBuilder stringBuilder = new StringBuilder();
//
//                InputStream in = socket.getInputStream();
//
//                int ch;
//
//                while ((ch = in.read()) != -1) {
//                stringBuilder.append((char) ch);
//                }
//
//                return stringBuilder.toString();

