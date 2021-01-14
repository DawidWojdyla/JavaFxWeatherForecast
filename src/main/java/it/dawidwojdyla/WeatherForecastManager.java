package it.dawidwojdyla;

import it.dawidwojdyla.controller.owm.WeatherService;
import it.dawidwojdyla.model.WeatherForecast;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dawid on 2021-01-11.
 */
public class WeatherForecastManager {

    public void fetchWeatherForecastData() {
        WeatherService weatherService = new WeatherService("", "");
        JSONArray dailyArray = weatherService.getDailyArray();

        System.out.println(dailyArray);
        System.out.println(dailyArray.length());

        List<WeatherForecast> weathers = new ArrayList<>();


        for (int i = 0; i < dailyArray.length(); i++) {
            JSONObject a = dailyArray.getJSONObject(i);
            System.out.println(a);
        }

    }
}
