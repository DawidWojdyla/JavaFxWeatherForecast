package it.dawidwojdyla.controller.services;

import it.dawidwojdyla.model.WeatherConditionsOfTheLocation;
import it.dawidwojdyla.model.WeatherForecast;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Dawid on 2021-01-14.
 */
public class FetchWeatherService extends Service<WeatherConditionsOfTheLocation> {

    private final String API_HOST = "https://api.openweathermap.org/data/2.5/onecall";
    private final String API_KEY = "39af7a169432c32cc8f937e351c91f46";
    private final String latitude;
    private final String longitude;
    private final String placeName;
    private String timeZone;
    private final List<WeatherForecast> forecasts = new ArrayList<>();
    WeatherConditionsOfTheLocation weatherConditionsOfTheLocation;


    public FetchWeatherService(String latitude, String longitude, String placeName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeName = placeName;

    }

    private String buildRequest() {
        return API_HOST + "?lat=" + latitude + "&lon=" + longitude +
                "&exclude=current,minutely,hourly,alerts&units=metric&appid=" + API_KEY;
    }

    @Override
    protected Task<WeatherConditionsOfTheLocation> createTask() {
        return new Task<>() {
            @Override
            protected WeatherConditionsOfTheLocation call() {

                JSONResponseFetcherOnHttpRequest responseFetcher = new JSONResponseFetcherOnHttpRequest(buildRequest());
                JSONObject jsonWeatherResponse = responseFetcher.getJSONResponse();
                timeZone = jsonWeatherResponse.optString("timezone");
                JSONArray dailyWeather = jsonWeatherResponse.getJSONArray("daily");
                for (int i = 0; i < 5; i++) {
                    JSONObject weatherOnDay = (JSONObject) dailyWeather.opt(i);
                    handleWeather(weatherOnDay);
                }

                weatherConditionsOfTheLocation = new WeatherConditionsOfTheLocation(latitude, longitude, placeName);
                weatherConditionsOfTheLocation.setWeatherForecasts(forecasts);
                return weatherConditionsOfTheLocation;
            }
        };
    }

    private void handleWeather(JSONObject jsonWeatherDay) {

        WeatherForecast weatherForecast = new WeatherForecast();

        JSONObject temperatures = (JSONObject) jsonWeatherDay.get("temp");

        weatherForecast.setMinTemp(String.format("%.1f", temperatures.optFloat("min")));
        weatherForecast.setMaxTemp(String.format("%.1f", temperatures.optFloat("max")));
        weatherForecast.setPressure(jsonWeatherDay.optString("pressure"));
        weatherForecast.setHumidity(jsonWeatherDay.optString("humidity"));
        weatherForecast.setProbabilityOfPrecipitation(jsonWeatherDay.optString("pop"));
        weatherForecast.setWindSpeed(jsonWeatherDay.optString("wind_speed"));
        weatherForecast.setClouds(jsonWeatherDay.optString("clouds"));
        weatherForecast.setSunrise(prepareDate(jsonWeatherDay.optLong("sunrise")));
        weatherForecast.setSunset(prepareDate(jsonWeatherDay.optLong("sunset")));
        JSONArray weather = (JSONArray) jsonWeatherDay.opt("weather");
        weatherForecast.setIconName(weather.getJSONObject(0).optString("icon"));
        weatherForecast.setDescription(weather.getJSONObject(0).optString("description"));

        if(jsonWeatherDay.has("rain")) {
            weatherForecast.setRain(jsonWeatherDay.optString("rain"));
        }
        if (jsonWeatherDay.has("snow")) {
            weatherForecast.setSnow(jsonWeatherDay.optString("snow"));
        }

        forecasts.add(weatherForecast);
    }

    private String prepareDate(long unixValue) {
        Date date = new Date(unixValue * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return simpleDateFormat.format(date);
    }
}