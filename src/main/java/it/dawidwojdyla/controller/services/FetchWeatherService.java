package it.dawidwojdyla.controller.services;

import it.dawidwojdyla.model.WeatherConditionsOfTheLocation;
import it.dawidwojdyla.model.Weather;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import static it.dawidwojdyla.model.constants.Constants.OPEN_WEATHER_ONE_CALL_API_HOST;
import static it.dawidwojdyla.model.constants.Constants.OPEN_WEATHER_ONE_CALL_API_KEY;

/**
 * Created by Dawid on 2021-01-14.
 */
public class FetchWeatherService extends Service<WeatherConditionsOfTheLocation> {

    private final String latitude;
    private final String longitude;
    private final String placeName;
    private String timeZone;
    private final List<Weather> weatherList = new ArrayList<>();
    WeatherConditionsOfTheLocation weatherConditionsOfTheLocation;

    public FetchWeatherService(String latitude, String longitude, String placeName) {
        this.latitude = latitude;
        this.longitude = longitude;
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

                HttpRequestManager httpRequestManager = new HttpRequestManager(buildRequest());
                JSONObject jsonWeatherResponse = httpRequestManager.getJSONResponse();
                timeZone = jsonWeatherResponse.optString("timezone");
                JSONArray dailyWeather = jsonWeatherResponse.getJSONArray("daily");
                for (int i = 0; i < dailyWeather.length(); i++) {
                    if (i == 5) {
                        break;
                    }
                    JSONObject jsonWeatherObject = (JSONObject) dailyWeather.opt(i);
                    handleWeather(jsonWeatherObject);
                }

                weatherConditionsOfTheLocation = new WeatherConditionsOfTheLocation(placeName);
                weatherConditionsOfTheLocation.setWeatherList(weatherList);
                return weatherConditionsOfTheLocation;
            }
        };
    }

    private void handleWeather(JSONObject jsonWeather) {

        Weather weather = new Weather();

        JSONObject temperatures = (JSONObject) jsonWeather.get("temp");
        weather.setMinTemp(Math.round(temperatures.optFloat("min")));
        weather.setMaxTemp(Math.round(temperatures.optFloat("max")));

        weather.setPressure(jsonWeather.optString("pressure"));
        weather.setHumidity(jsonWeather.optString("humidity"));
        weather.setProbabilityOfPrecipitation((int)(jsonWeather.optFloat("pop") * 100) + "%");
        weather.setWindSpeed(jsonWeather.optString("wind_speed"));
        weather.setCloudiness(jsonWeather.optString("clouds") + "%");
        weather.setSunrise(prepareDateTimeInRequiredFormat(jsonWeather.optLong("sunrise"), "HH:mm"));
        weather.setSunset(prepareDateTimeInRequiredFormat(jsonWeather.optLong("sunset"), "HH:mm"));
        weather.setDate(prepareDateTimeInRequiredFormat(jsonWeather.optLong("sunset"), "dd.MM EEEE"));

        JSONArray weatherDescription = (JSONArray) jsonWeather.opt("weather");
        weather.setIconName(weatherDescription.getJSONObject(0).optString("icon"));
        weather.setDescription(weatherDescription.getJSONObject(0).optString("description"));
        weather.setRain(jsonWeather.optString("rain", "no rain"));
        weather.setSnow(jsonWeather.optString("snow", "no snow"));

        weatherList.add(weather);
    }

    private String prepareDateTimeInRequiredFormat(long unixValue, String pattern) {
        Date date = new Date(unixValue * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return simpleDateFormat.format(date);
    }
}