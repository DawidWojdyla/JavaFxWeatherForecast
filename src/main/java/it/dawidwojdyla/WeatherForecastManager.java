package it.dawidwojdyla;

import it.dawidwojdyla.controller.MainWindowController;
import it.dawidwojdyla.controller.services.FetchWeatherService;
import it.dawidwojdyla.model.WeatherConditionsOfTheLocation;
import javafx.concurrent.Service;


/**
 * Created by Dawid on 2021-01-11.
 */
public class WeatherForecastManager {

    private static final String CURRENT_LOCATION_LATITUDE_DEFAULT = "50.0412";
    private static final String CURRENT_LOCATION_LONGITUDE_DEFAULT = "21.9991";
    private static final String CURRENT_LOCATION_NAME = "Rzeszów, Poland";
    private static final String DESTINATION_LATITUDE_DEFAULT = "45.4420043";
    private static final String DESTINATION_LONGITUDE_DEFAULT = "12.3378095";
    private static final String DESTINATION_NAME = "Venice, Italy";

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
            currentLocationForecast.setPlaceName(CURRENT_LOCATION_NAME);
            mainWindowController.setCurrentLocationForecast();
        });

        currentLocationForecastFetcher.start();

        destinationForecastFetcher = new FetchWeatherService(this,
                DESTINATION_LATITUDE_DEFAULT, DESTINATION_LONGITUDE_DEFAULT);
        destinationForecastFetcher.setOnSucceeded(e -> {
            destinationForecast = destinationForecastFetcher.getValue();
            destinationForecast.setPlaceName(DESTINATION_NAME);
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
