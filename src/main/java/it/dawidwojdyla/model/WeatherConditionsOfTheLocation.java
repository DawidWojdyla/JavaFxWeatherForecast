package it.dawidwojdyla.model;

import java.util.List;

/**
 * Created by Dawid on 2021-01-14.
 */
public class WeatherConditionsOfTheLocation {

    private final List<Weather> weatherList;
    private final String placeName;

    public WeatherConditionsOfTheLocation(String placeName, List<Weather> weatherList) {
        this.placeName = placeName;
        this.weatherList = weatherList;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public String getPlaceName() {
        return placeName;
    }
}
