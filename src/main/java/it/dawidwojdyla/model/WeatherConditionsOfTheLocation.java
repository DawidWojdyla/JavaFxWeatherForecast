package it.dawidwojdyla.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dawid on 2021-01-14.
 */
public class WeatherConditionsOfTheLocation {

    private List<Weather> weatherList = new ArrayList<>();
    private String placeName;

    public WeatherConditionsOfTheLocation(String placeName) {
        this.placeName = placeName;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public String getPlaceName() {
        return placeName;
    }
}
