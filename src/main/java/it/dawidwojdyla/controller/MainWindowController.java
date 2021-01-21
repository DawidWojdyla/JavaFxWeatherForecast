package it.dawidwojdyla.controller;

import it.dawidwojdyla.WeatherForecastManager;
import it.dawidwojdyla.controller.services.FetchGeoCoordinatesService;
import it.dawidwojdyla.model.SearchCityResult;
import it.dawidwojdyla.model.WeatherConditionsOfTheLocation;
import it.dawidwojdyla.model.WeatherForecast;
import it.dawidwojdyla.view.WeatherDayViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Dawid on 2021-01-11.
 */
public class MainWindowController implements Initializable {


    @FXML
    private TextField currentLocationTextField;

    @FXML
    private VBox currentLocationWeatherVBox;

    @FXML
    private AnchorPane currentLocationSearchResultPane;

    @FXML
    private VBox currentLocationSearchResultVBox;

    @FXML
    private TextField destinationTextField;

    @FXML
    private VBox destinationWeatherVBox;

    @FXML
    private AnchorPane destinationSearchResultPane;

    @FXML
    private VBox destinationSearchResultVBox;


    WeatherForecastManager weatherForecastManager;

    public MainWindowController(WeatherForecastManager weatherForecastManager) {
        this.weatherForecastManager = weatherForecastManager;
    }

    @FXML
    void currentLocationSearchBackButtonAction() {
        currentLocationSearchResultPane.setVisible(false);
    }

    @FXML
    void destinationSearchBackButtonAction() {
        destinationSearchResultPane.setVisible(false);
    }

    @FXML
    void currentLocationSearchButtonAction() {

        fetchGeoCoordinates(currentLocationTextField.getText(), currentLocationSearchResultVBox,
                currentLocationWeatherVBox, currentLocationSearchResultPane);
    }

    @FXML
    void destinationSearchButtonAction() {
        fetchGeoCoordinates(destinationTextField.getText(), destinationSearchResultVBox,
                destinationWeatherVBox, destinationSearchResultPane);
    }

    private void fetchGeoCoordinates(String searchText, VBox resultVBox, VBox weatherForecastVBox, AnchorPane searchResultPane) {
        FetchGeoCoordinatesService geoCoordinateService = new FetchGeoCoordinatesService(searchText);
        geoCoordinateService.setOnSucceeded(e -> {
            resultVBox.getChildren().clear();
            for (SearchCityResult result: geoCoordinateService.getValue()) {
                Label label = new Label(result.getSearchResultDisplayText());
                label.setMaxWidth(Double.MAX_VALUE);
                label.getStyleClass().add("searching-result-label");
                label.setOnMouseClicked(e1 -> {
                    weatherForecastManager.fetchWeather(result, weatherForecastVBox);
                    searchResultPane.setVisible(false);
                });
                resultVBox.getChildren().add(label);
            }
            searchResultPane.setVisible(true);
        });
        geoCoordinateService.start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        weatherForecastManager.setMainWindowController(this);
        weatherForecastManager.fetchDefaultLocationsWeather(currentLocationWeatherVBox, destinationWeatherVBox);
    }

    public void setWeatherForecast(WeatherConditionsOfTheLocation weatherConditionsOfTheLocation, VBox weatherForecastVBox) {
        weatherForecastVBox.getChildren().clear();
        Label placeNameLabel = new Label(weatherConditionsOfTheLocation.getPlaceName());
        placeNameLabel.getStyleClass().add("place-name-label");
        weatherForecastVBox.getChildren().add(placeNameLabel);
        for (WeatherForecast weatherForecast: weatherConditionsOfTheLocation.getWeatherForecasts()) {
            weatherForecastVBox.getChildren().add(WeatherDayViewFactory.createWeatherDayAnchorPane(weatherForecast));
        }
    }

}