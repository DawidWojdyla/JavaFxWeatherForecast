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
    String fxmlName;

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
                label.getStyleClass().add("searchingResultLabel");
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
        placeNameLabel.getStyleClass().add("placeNameLabel");
        weatherForecastVBox.getChildren().add(placeNameLabel);
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
        anchorPane.setMinSize(100,100);
        anchorPane.getStyleClass().add("weather-item-pane");
        anchorPane.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, "
                + getRGBColor(weatherForecast.getMinTemp()) + ", " + getRGBColor(weatherForecast.getMaxTemp()) + ")");

        return anchorPane;
    }


    private String getRGBColor(double temperature){
        double red;
        double green;
        double blue;
        double factor = 150 - 1.69 * (40 + temperature);

        if (factor <= 66) {
            red = 255;
            green = 99.4708025861 * Math.log(factor) - 161.1195681661;

            if (factor <= 19) {
                blue = 0;
            } else {
                blue = factor - 10;
                blue = 138.5177312231 * Math.log(blue) - 305.0447927307;
            }
        } else {

            red = factor - 60;
            red = 329.698727466 * Math.pow(red, -0.1332047592);

            green = factor - 60;
            green = 288.1221695283 * Math.pow(green, -0.0755148492);

            blue = 255;
        }

        if (red < 0) {
            red = 0;
        } else if(red > 255) {
            red = 255;
        }
        if (green < 0 || Double.isNaN(green)) {
            green = 0;
        } else if (green > 255) {
            green = 255;
        }

        if (blue < 0) {
            blue = 0;
        } else if (blue > 255) {
            blue = 255;
        }

        int r = (int) red;
        int g = (int) green;
        int b = (int) blue;

        return "rgb(" + r + ", " + g + ", " + b + ")";
    }
}