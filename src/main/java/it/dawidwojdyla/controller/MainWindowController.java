package it.dawidwojdyla.controller;

import it.dawidwojdyla.Launcher;
import it.dawidwojdyla.WeatherForecastManager;
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
    private TextField destinationTextField;

    @FXML
    private VBox destinationWeatherVBox;

    @FXML
    private VBox currentLocationWeatherVBox;

    @FXML
    private Label currentLocalizationName;

    @FXML
    private Label destinationName;

    @FXML
    void currentLocationButtonAction() {

    }

    @FXML
    void destinationButtonAction() {

    }

    WeatherForecastManager weatherForecastManager;
    String fxmlName;

    private WeatherConditionsOfTheLocation currentLocationForecast;
    private WeatherConditionsOfTheLocation destinationForecast;


    public MainWindowController(WeatherForecastManager weatherForecastManager, String fxmlName) {
        this.weatherForecastManager = weatherForecastManager;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setCurrentLocationForecast() {
        System.out.println("SETTING UP FORECAST FOR CURRENT LOCATION");
        currentLocationForecast = weatherForecastManager.getCurrentLocationForecast();
        currentLocalizationName.setText(currentLocationForecast.getPlaceName());
        currentLocationWeatherVBox.getChildren().removeAll();
        for(int i = 0; i < currentLocationForecast.getWeatherForecasts().size(); i++) {
            WeatherForecast weatherForecast = currentLocationForecast.getWeatherForecasts().get(i);
            currentLocationWeatherVBox.getChildren().add(returnWeatherDayAnchorPane(weatherForecast));
        }
    }

    public void setDestinationForecast() {
        System.out.println("SETTING UP FORECAST FOR DESTINATION");
        destinationForecast = weatherForecastManager.getDestinationForecast();
        destinationName.setText(destinationForecast.getPlaceName());
        destinationWeatherVBox.getChildren().removeAll();

        for (int i = 0; i < destinationForecast.getWeatherForecasts().size(); i++) {
            WeatherForecast weatherForecast = destinationForecast.getWeatherForecasts().get(i);
            destinationWeatherVBox.getChildren().add(returnWeatherDayAnchorPane(weatherForecast));
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
