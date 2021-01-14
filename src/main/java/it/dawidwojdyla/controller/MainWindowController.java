package it.dawidwojdyla.controller;

import it.dawidwojdyla.WeatherForecastManager;
import it.dawidwojdyla.controller.owm.OpenWeatherMap;
import it.dawidwojdyla.model.WeatherConditionsOfTheLocation;
import it.dawidwojdyla.model.WeatherForecast;
import it.dawidwojdyla.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;


/**
 * Created by Dawid on 2021-01-11.
 */
public class MainWindowController implements Initializable {


    @FXML
    private Label leftLabel;

    @FXML
    private TextField leftTextField;

    @FXML
    private Label rightLabel;

    @FXML
    private TextField rightTextField;

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

    @FXML
    void leftButtonAction() {
        System.out.println("left button...");
    }

    @FXML
    void rightButtonAction() {
        System.out.println("right button...");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setCurrentLocationForecast() {
        System.out.println("SETTING UP FORECAST FOR CURRENT LOCATION");
        currentLocationForecast = weatherForecastManager.getCurrentLocationForecast();
        leftLabel.setText(currentLocationForecast.getLatitude());

        for(int i = 0; i < currentLocationForecast.getWeatherForecasts().size(); i++) {
            WeatherForecast weatherForecast = currentLocationForecast.getWeatherForecasts().get(i);
            leftLabel.setText(leftLabel.getText() + "/nMintemp: "+ weatherForecast.getMaxTemp());
        }


    }

    public void setDestinationForecast() {
        System.out.println("SETTING UP FORECAST FOR DESTIONATION");
        destinationForecast = weatherForecastManager.getDestinationForecast();
        rightLabel.setText(destinationForecast.getLatitude());

        for(int i = 0; i < destinationForecast.getWeatherForecasts().size(); i++) {
            WeatherForecast weatherForecast = destinationForecast.getWeatherForecasts().get(i);
            rightLabel.setText(rightLabel.getText() + "/nMintemp: "+ weatherForecast.getMaxTemp());
        }
    }
}
