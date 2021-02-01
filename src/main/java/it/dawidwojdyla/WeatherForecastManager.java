package it.dawidwojdyla;

import it.dawidwojdyla.controller.MainWindowController;
import it.dawidwojdyla.controller.services.FetchWeatherService;
import it.dawidwojdyla.model.SearchCityResult;
import javafx.scene.layout.VBox;

import static it.dawidwojdyla.model.Constants.*;


/**
 * Created by Dawid on 2021-01-11.
 */
public class WeatherForecastManager {

    private MainWindowController mainWindowController;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void fetchDefaultLocationsWeather(VBox currentLocationWeatherVBox, VBox destinationWeatherVBox) {

        fetchWeather(new SearchCityResult(CURRENT_LOCATION_LATITUDE_DEFAULT, CURRENT_LOCATION_LONGITUDE_DEFAULT,
                CURRENT_LOCATION_CITY_DEFAULT, CURRENT_LOCATION_COUNTRY_DEFAULT), currentLocationWeatherVBox);

        fetchWeather(new SearchCityResult(DESTINATION_LATITUDE_DEFAULT, DESTINATION_LONGITUDE_DEFAULT,
                DESTINATION_CITY_DEFAULT, DESTINATION_COUNTRY_DEFAULT), destinationWeatherVBox);
    }

    public void fetchWeather(SearchCityResult result, VBox weatherVBox) {
        FetchWeatherService fetchWeatherService = new FetchWeatherService(
                result.getLatitude(), result.getLongitude(), result.getDisplayName());
        fetchWeatherService.setOnSucceeded(e ->
                mainWindowController.showWeather(fetchWeatherService.getValue(), weatherVBox));
        fetchWeatherService.setOnFailed(e -> mainWindowController.showMessage(weatherVBox, CONNECTION_FAILED_MESSAGE));
        fetchWeatherService.start();
    }

}
