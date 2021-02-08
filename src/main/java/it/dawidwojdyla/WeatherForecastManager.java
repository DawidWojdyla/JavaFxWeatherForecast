package it.dawidwojdyla;

import it.dawidwojdyla.controller.MainWindowController;
import it.dawidwojdyla.controller.services.FetchWeatherService;
import it.dawidwojdyla.model.Constants;
import it.dawidwojdyla.model.SearchCityResult;
import javafx.scene.layout.VBox;

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

    public void fetchWeather(SearchCityResult result, VBox weatherVBox) {
        FetchWeatherService service = setFetchWeatherService();
        service.setLatitude(result.getLatitude());
        service.setLongitude(result.getLongitude());
        service.setPlaceName(result.getDisplayName());
        service.setOnSucceeded(e -> mainWindowController.showWeather(service.getValue(), weatherVBox));
        service.setOnFailed(e -> mainWindowController.showMessage(weatherVBox, Constants.CONNECTION_FAILED_MESSAGE));
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
