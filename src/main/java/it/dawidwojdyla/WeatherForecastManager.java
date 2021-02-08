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
    private final FetchWeatherService fetchWeatherService;
    private final FetchWeatherService fetchWeatherServiceSpare;

    public WeatherForecastManager() {
        fetchWeatherService = new FetchWeatherService();
        fetchWeatherServiceSpare = new FetchWeatherService();
    }

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
        FetchWeatherService service = setFetchWeatherService();
        service.setLatitude(result.getLatitude());
        service.setLongitude(result.getLongitude());
        service.setPlaceName(result.getDisplayName());
        service.setOnSucceeded(e -> mainWindowController.showWeather(service.getValue(), weatherVBox));
        service.setOnFailed(e -> mainWindowController.showMessage(weatherVBox, CONNECTION_FAILED_MESSAGE));
        service.restart();
    }

    private FetchWeatherService setFetchWeatherService() {
        if (!fetchWeatherService.isRunning()) {
            return fetchWeatherService;
        } else if (!fetchWeatherServiceSpare.isRunning()) {
            return fetchWeatherServiceSpare;
        } else {
            return new FetchWeatherService();
        }
    }
}
