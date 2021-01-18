package it.dawidwojdyla.controller;

import it.dawidwojdyla.Launcher;
import it.dawidwojdyla.WeatherForecastManager;
import it.dawidwojdyla.controller.services.FetchGeoCoordinatesService;
import it.dawidwojdyla.model.SearchCityResult;
import it.dawidwojdyla.model.WeatherConditionsOfTheLocation;
import it.dawidwojdyla.model.WeatherForecast;
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
    private Label currentLocationName;

    @FXML
    private AnchorPane currentLocationSearchResultPane;

    @FXML
    private VBox currentLocationSearchResultVBox;

    @FXML
    private TextField destinationTextField;

    @FXML
    private VBox destinationWeatherVBox;

    @FXML
    private Label destinationName;

    @FXML
    private AnchorPane destinationSearchResultPane;

    @FXML
    private VBox destinationSearchResultVBox;


    WeatherForecastManager weatherForecastManager;
    String fxmlName;

    public MainWindowController(WeatherForecastManager weatherForecastManager, String fxmlName) {
        this.weatherForecastManager = weatherForecastManager;
        this.fxmlName = fxmlName;
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

        fetchGeoCoordinates(currentLocationTextField.getText(), currentLocationSearchResultVBox, currentLocationName,
                currentLocationWeatherVBox, currentLocationSearchResultPane);
    }

    @FXML
    void destinationSearchButtonAction() {
        fetchGeoCoordinates(destinationTextField.getText(), destinationSearchResultVBox, destinationName,
                destinationWeatherVBox, destinationSearchResultPane);
    }

    private void fetchGeoCoordinates(String searchText, VBox resultVBox, Label placeNameLabel, VBox weatherForecastVBox, AnchorPane searchResultPane) {
        FetchGeoCoordinatesService geoCoordinateService = new FetchGeoCoordinatesService(searchText);
        geoCoordinateService.setOnSucceeded(e -> {
            resultVBox.getChildren().clear();
            for (SearchCityResult result: geoCoordinateService.getValue()) {
                Label label = new Label(result.getSearchResultDisplayText());
                label.setMaxWidth(Double.MAX_VALUE);
                label.getStyleClass().add("searchingResultLabel");
                label.setOnMouseClicked(e1 -> {
                    weatherForecastManager.fetchWeather(result, placeNameLabel, weatherForecastVBox);
                    searchResultPane.setVisible(false);
                });
                resultVBox.getChildren().add(label);
            }
            searchResultPane.setVisible(true);
        });
        geoCoordinateService.start();
    }

    public String getFxmlName() {
        return fxmlName;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        weatherForecastManager.fetchDefaultLocationsWeather(currentLocationWeatherVBox, currentLocationName,
                destinationWeatherVBox, destinationName);
    }

    public void setWeatherForecast(WeatherConditionsOfTheLocation weatherConditionsOfTheLocation,
                                   Label placeNameLabel, VBox weatherForecastVBox) {
        placeNameLabel.setText(weatherConditionsOfTheLocation.getPlaceName());
        weatherForecastVBox.getChildren().clear();
        for (WeatherForecast weatherForecast: weatherConditionsOfTheLocation.getWeatherForecasts()) {
            weatherForecastVBox.getChildren().add(returnWeatherDayAnchorPane(weatherForecast));
        }
    }

    private AnchorPane returnWeatherDayAnchorPane(WeatherForecast weatherForecast) {
        AnchorPane anchorPane = new AnchorPane();

        Label date = new Label(weatherForecast.getSunrise().substring(0, 5));
        AnchorPane.setTopAnchor(date, 5.0);
        AnchorPane.setLeftAnchor(date, 5.0);
        date.setStyle("-fx-font-size: 15px");

        Label description = new Label(weatherForecast.getDescription());
        AnchorPane.setTopAnchor(description, 2.0);
        AnchorPane.setRightAnchor(description, 90.0);
        description.setStyle("-fx-font-size: 15px");

        Label temperature = new Label(
                weatherForecast.getMinTemp() + "ºC / " + weatherForecast.getMaxTemp() + "ºC");
        AnchorPane.setTopAnchor(temperature, 65.0);
        AnchorPane.setRightAnchor(temperature, 5.0);
        temperature.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        Image image = new Image(Launcher.class.getResourceAsStream
                ("icons/" + weatherForecast.getIconName() + "@2x.png"));
        ImageView imageView = new ImageView(image);
        AnchorPane.setTopAnchor(imageView,-20.0);
        AnchorPane.setRightAnchor(imageView,2.0);

        anchorPane.getChildren().addAll(date, temperature, imageView, description);
        anchorPane.setMinSize(100,97);
        anchorPane.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0.5px 0px 0.5px 0px");
        return anchorPane;
    }
}
