package it.dawidwojdyla.controller.services;

import it.dawidwojdyla.model.WeatherConditionsOfTheLocation;
import it.dawidwojdyla.model.Weather;
import it.dawidwojdyla.model.Constants;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static it.dawidwojdyla.model.Constants.OPEN_WEATHER_ONE_CALL_API_HOST;
import static it.dawidwojdyla.model.Constants.OPEN_WEATHER_ONE_CALL_API_KEY;

/**
 * Created by Dawid on 2021-01-14.
 */
public class FetchWeatherService extends Service<WeatherConditionsOfTheLocation> {

    private String latitude;
    private String longitude;
    private String placeName;

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    private String buildRequest() {
        return OPEN_WEATHER_ONE_CALL_API_HOST + "?lat=" + latitude + "&lon=" + longitude +
                "&exclude=current,minutely,hourly,alerts&units=metric&appid=" + OPEN_WEATHER_ONE_CALL_API_KEY;
    }

    @Override
    protected Task<WeatherConditionsOfTheLocation> createTask() {
        return new Task<>() {
            @Override
            protected WeatherConditionsOfTheLocation call() {
                List<Weather> weatherList = new ArrayList<>();

                HttpRequestManager httpRequestManager = new HttpRequestManager(buildRequest());
                JSONObject jsonWeatherResponse = httpRequestManager.getJSONResponse();
                if (jsonWeatherResponse != null) {
                    String timeZone = jsonWeatherResponse.optString("timezone");
                    JSONArray dailyWeather = jsonWeatherResponse.getJSONArray("daily");
                    for (int i = 0; i < dailyWeather.length() && i < Constants.MAX_WEATHER_DAY_AMOUNT; i++) {
                        JSONObject jsonWeatherObject = dailyWeather.getJSONObject(i);
                        weatherList.add(handleWeather(jsonWeatherObject, timeZone));
                    }
                    return new WeatherConditionsOfTheLocation(placeName, weatherList);
                }
                return null;
            }
        };
    }

    private Weather handleWeather(JSONObject jsonWeather, String timeZone) {

        JSONObject temperatures = jsonWeather.getJSONObject("temp");
        int minTemp = Math.round(temperatures.optFloat("min"));
        int maxTemp = Math.round(temperatures.optFloat("max"));

        String pressure = jsonWeather.optString("pressure");
        String humidity = jsonWeather.optString("humidity");
        String probabilityOfPrecipitation = (int) (jsonWeather.optFloat("pop") * 100) + "%";
        String windSpeed = jsonWeather.optString("wind_speed");
        String cloudiness = jsonWeather.optString("clouds") + "%";
        String sunrise = prepareDateTimeInRequiredFormat(jsonWeather.optLong("sunrise"), "HH:mm", timeZone);
        String sunset = prepareDateTimeInRequiredFormat(jsonWeather.optLong("sunset"), "HH:mm", timeZone);
        String date = prepareDateTimeInRequiredFormat(jsonWeather.optLong("sunset"), "dd.MM EEEE", timeZone);

        JSONArray weatherDescription = jsonWeather.getJSONArray("weather");
        String iconName = weatherDescription.getJSONObject(0).optString("icon");
        String description = weatherDescription.getJSONObject(0).optString("description");
        String rain = jsonWeather.optString("rain", "no rain");
        String snow = jsonWeather.optString("snow", "no snow");

        return new Weather(minTemp, maxTemp, probabilityOfPrecipitation, rain, snow, pressure, humidity, cloudiness,
                windSpeed, sunset, sunrise, description, iconName, date);
    }

    private String prepareDateTimeInRequiredFormat(long unixValue, String pattern, String timeZone) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(unixValue), ZoneId.of(timeZone))
                .format(DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH));
    }
}