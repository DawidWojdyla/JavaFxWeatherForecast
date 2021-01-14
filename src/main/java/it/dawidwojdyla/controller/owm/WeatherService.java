package it.dawidwojdyla.controller.owm;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by Dawid on 2021-01-13.
 */
public class WeatherService {

    String host = "https://api.openweathermap.org/data/2.5/onecall";
    String apiKey = "39af7a169432c32cc8f937e351c91f46";
    String lat;
    String lon;
    String id;

    public WeatherService(String lat, String lon) {
        this.lat = "50.177791";
        this.lon = "22.609539";
        this.id = "759301";
    }

    private String buildRequest() {
        return host + "?lat=" + lat + "&lon=" + lon + "&exclude=current,minutely,hourly,alerts&units=metric&appid=" + apiKey;
    }

    public JSONObject getWeatherForecast() {

        try {
            String request = buildRequest();
            HttpURLConnection connection = (HttpURLConnection) new URL(request).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.connect();

            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine);
            }
            System.out.println(response);
            return new JSONObject(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

   public JSONArray getDailyArray() {
        return getWeatherForecast().getJSONArray("daily");
   }
}
