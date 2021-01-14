package it.dawidwojdyla;

import it.dawidwojdyla.controller.MainWindowController;
import it.dawidwojdyla.controller.services.FetchWeatherService;
import it.dawidwojdyla.model.WeatherConditionsOfTheLocation;
import javafx.concurrent.Service;


/**
 * Created by Dawid on 2021-01-11.
 */
public class WeatherForecastManager {

    private static final String CURRENT_LOCATION_LATITUDE_DEFAULT = "50.177791";
    private static final String CURRENT_LOCATION_LONGITUDE_DEFAULT = "22.609539";
    private static final String DESTINATION_LATITUDE_DEFAULT = "50.3185408";
    private static final String DESTINATION_LONGITUDE_DEFAULT = "18.788618";

    private WeatherConditionsOfTheLocation currentLocationForecast;
    private WeatherConditionsOfTheLocation destinationForecast;

    private MainWindowController mainWindowController;
    private Service<WeatherConditionsOfTheLocation> currentLocationForecastFetcher;
    private Service<WeatherConditionsOfTheLocation> destinationForecastFetcher;

    public WeatherForecastManager() {

        currentLocationForecastFetcher = new FetchWeatherService(this,
                CURRENT_LOCATION_LATITUDE_DEFAULT, CURRENT_LOCATION_LONGITUDE_DEFAULT);

        currentLocationForecastFetcher.setOnSucceeded(e -> {
            currentLocationForecast = currentLocationForecastFetcher.getValue();
            mainWindowController.setCurrentLocationForecast();
        });

        currentLocationForecastFetcher.start();

        destinationForecastFetcher = new FetchWeatherService(this,
                DESTINATION_LATITUDE_DEFAULT, DESTINATION_LONGITUDE_DEFAULT);
        destinationForecastFetcher.setOnSucceeded(e -> {
            destinationForecast = destinationForecastFetcher.getValue();
            mainWindowController.setDestinationForecast();
        });

        destinationForecastFetcher.start();
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public WeatherConditionsOfTheLocation getCurrentLocationForecast() {
        return currentLocationForecast;
    }

    public void setCurrentLocationForecast(WeatherConditionsOfTheLocation currentLocationForecast) {
        this.currentLocationForecast = currentLocationForecast;
    }

    public WeatherConditionsOfTheLocation getDestinationForecast() {
        return destinationForecast;
    }

    public void setDestinationForecast(WeatherConditionsOfTheLocation destinationForecast) {
        this.destinationForecast = destinationForecast;
    }

}
