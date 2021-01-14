package it.dawidwojdyla.controller.services;

import it.dawidwojdyla.WeatherForecastManager;
import it.dawidwojdyla.controller.owm.OpenWeatherMap;
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

    private final String latitude;
    private final String longitude;
    private String timeZone;
    private WeatherForecastManager weatherForecastManager;
    private List<WeatherForecast> forecasts = new ArrayList<>();
    WeatherConditionsOfTheLocation weatherConditionsOfTheLocation;


    public FetchWeatherService(WeatherForecastManager weatherForecastManager, String latitude, String longitude) {
        this.weatherForecastManager = weatherForecastManager;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    @Override
    protected Task<WeatherConditionsOfTheLocation> createTask() {
        return new Task<>() {
            @Override
            protected WeatherConditionsOfTheLocation call() {

                OpenWeatherMap openWeatherMap = new OpenWeatherMap(latitude, longitude);
                JSONObject jsonWeatherResponse = openWeatherMap.getWeatherForecast();
                timeZone = jsonWeatherResponse.optString("timezone");
                JSONArray dailyWeather = jsonWeatherResponse.getJSONArray("daily");
                for (int i = 0; i < dailyWeather.length(); i++) {
                    if (i == 5) {
                        break;
                    }
                    JSONObject weatherOnDay = (JSONObject) dailyWeather.opt(i);
                    handleWeather(weatherOnDay);
                }

                weatherConditionsOfTheLocation = new WeatherConditionsOfTheLocation(latitude, longitude);
                weatherConditionsOfTheLocation.setWeatherForecasts(forecasts);
                return weatherConditionsOfTheLocation;
            }
        };
    }

    private void handleWeather(JSONObject jsonWeatherDay) {

        WeatherForecast weatherForecast = new WeatherForecast();

        JSONObject temperatures = (JSONObject) jsonWeatherDay.get("temp");
        weatherForecast.setMinTemp(temperatures.optString("min"));
        weatherForecast.setMaxTemp(temperatures.optString("max"));

        weatherForecast.setPressure(jsonWeatherDay.optString("pressure"));
        weatherForecast.setHumidity(jsonWeatherDay.optString("humidity"));
        weatherForecast.setProbabilityOfPrecipitation(jsonWeatherDay.optString("pop"));
        weatherForecast.setWindSpeed(jsonWeatherDay.optString("wind_speed"));
        weatherForecast.setSunrise(prepareDate(jsonWeatherDay.getLong("sunrise")));
        weatherForecast.setSunset(prepareDate(jsonWeatherDay.getLong("sunset")));
        JSONObject weather = (JSONObject) jsonWeatherDay.opt("weather");
        weatherForecast.setIconName(weather.optString("icon"));
        weatherForecast.setDescription(weather.optString("description"));

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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return simpleDateFormat.format(date);
    }
}
