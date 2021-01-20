package it.dawidwojdyla.controller;

import it.dawidwojdyla.Launcher;
import it.dawidwojdyla.WeatherForecastManager;
import it.dawidwojdyla.controller.services.FetchGeoCoordinatesService;
import it.dawidwojdyla.model.SearchCityResult;
import it.dawidwojdyla.model.WeatherConditionsOfTheLocation;
import it.dawidwojdyla.model.WeatherForecast;
import it.dawidwojdyla.view.RGBColorFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
            weatherForecastVBox.getChildren().add(returnWeatherDayAnchorPane(weatherForecast));
        }
    }

    private AnchorPane returnWeatherDayAnchorPane(WeatherForecast weatherForecast) {
        AnchorPane anchorPane = new AnchorPane();

        Label date = new Label(weatherForecast.getDate());
        AnchorPane.setTopAnchor(date, 1.0);
        AnchorPane.setLeftAnchor(date, 2.0);
        date.setStyle("-fx-font-size: 15px");

        Image sun = new Image(Launcher.class.getResourceAsStream
                ("icons/sun_small.png"));
        ImageView sunImageView = new ImageView(sun);
        AnchorPane.setTopAnchor(sunImageView, 5.0);
        AnchorPane.setLeftAnchor(sunImageView, 135.0);

        Label sunrise = new Label(weatherForecast.getSunrise());
        AnchorPane.setTopAnchor(sunrise, 2.0);
        AnchorPane.setLeftAnchor(sunrise, 155.0);


        Image moon = new Image(Launcher.class.getResourceAsStream
                ("icons/moon_small.png"));
        ImageView moonImageView = new ImageView(moon);
        AnchorPane.setTopAnchor(moonImageView, 5.0);
        AnchorPane.setLeftAnchor(moonImageView, 190.0);

        Label sunset = new Label(weatherForecast.getSunset());
        AnchorPane.setTopAnchor(sunset, 2.0);
        AnchorPane.setLeftAnchor(sunset, 205.0);


        Label description = new Label(weatherForecast.getDescription());
        AnchorPane.setTopAnchor(description, 50.0);
        AnchorPane.setLeftAnchor(description, 60.0);
        description.setStyle("-fx-font-size: 18px;");

        Label temperature = new Label(
                weatherForecast.getMaxTemp() + "ºC / " + weatherForecast.getMinTemp() + "ºC");
        AnchorPane.setBottomAnchor(temperature, -1.0);
        AnchorPane.setRightAnchor(temperature, 3.0);
        temperature.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label tempDescription = new Label("max /  min");
        AnchorPane.setBottomAnchor(tempDescription, 25.0);
        AnchorPane.setRightAnchor(tempDescription, 15.0);

        Image mainIcon = new Image(Launcher.class.getResourceAsStream
                ("icons/" + weatherForecast.getIconName() + "@2x.png"));
        ImageView mainImageView = new ImageView(mainIcon);
        AnchorPane.setTopAnchor(mainImageView,-20.0);
        AnchorPane.setRightAnchor(mainImageView,2.0);

        anchorPane.getChildren().addAll(date, sunrise, sunImageView, moonImageView, sunset, temperature, mainImageView, description, tempDescription);
        anchorPane.setMinSize(100,100);
        anchorPane.getStyleClass().add("weather-item-pane");
        anchorPane.setStyle("-fx-background-color: linear-gradient(to right, "
                + RGBColorFactory.generateColorFromTemperature(weatherForecast.getMaxTemp()) +
                ", " + RGBColorFactory.generateColorFromTemperature(weatherForecast.getMinTemp()) + ")");

        return anchorPane;
    }
}