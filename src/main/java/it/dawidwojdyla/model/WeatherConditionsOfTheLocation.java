package it.dawidwojdyla.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dawid on 2021-01-14.
 */
public class WeatherConditionsOfTheLocation {

    private List<WeatherForecast> weatherForecasts = new ArrayList<>();
    private String placeName;
    private String latitude;
    private String longitude;

    public WeatherConditionsOfTheLocation(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<WeatherForecast> getWeatherForecasts() {
        return weatherForecasts;
    }

    public void setWeatherForecasts(List<WeatherForecast> weatherForecasts) {
        this.weatherForecasts = weatherForecasts;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
}
