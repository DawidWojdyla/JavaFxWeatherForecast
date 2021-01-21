package it.dawidwojdyla.controller;

import it.dawidwojdyla.Launcher;
import it.dawidwojdyla.WeatherForecastManager;
import it.dawidwojdyla.controller.services.FetchGeoCoordinatesService;
import it.dawidwojdyla.model.SearchCityResult;
import it.dawidwojdyla.model.WeatherConditionsOfTheLocation;
import it.dawidwojdyla.model.WeatherForecast;
import it.dawidwojdyla.view.WeatherDayViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private Button currentLocationSearchButton;

    @FXML
    private VBox currentLocationWeatherVBox;

    @FXML
    private AnchorPane currentLocationSearchResultPane;

    @FXML
    private VBox currentLocationSearchResultVBox;

    @FXML
    private TextField destinationTextField;

    @FXML
    private Button destinationSearchButton;

    @FXML
    private VBox destinationWeatherVBox;

    @FXML
    private AnchorPane destinationSearchResultPane;

    @FXML
    private VBox destinationSearchResultVBox;

    @FXML
    private Label currentLocationInfoLabel;

    @FXML
    private Label destinationInfoLabel;



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
        currentLocationInfoLabel.setText("");
        if (currentLocationTextField.getText().length() > 2) {
            fetchGeoCoordinates(currentLocationTextField.getText(), currentLocationSearchResultVBox,
                    currentLocationWeatherVBox, currentLocationSearchResultPane);
        } else {
            currentLocationInfoLabel.setText("Enter at least 3 characters");
        }
    }

    @FXML
    void destinationSearchButtonAction() {
        destinationInfoLabel.setText("");
        if (destinationTextField.getText().length() > 2) {
            fetchGeoCoordinates(destinationTextField.getText(), destinationSearchResultVBox,
                    destinationWeatherVBox, destinationSearchResultPane);
        } else {
            destinationInfoLabel.setText("Enter at least 3 characters");
        }
    }

    private void fetchGeoCoordinates(String searchText, VBox resultVBox, VBox weatherForecastVBox, AnchorPane searchResultPane) {
        FetchGeoCoordinatesService geoCoordinateService = new FetchGeoCoordinatesService(searchText);
        geoCoordinateService.setOnSucceeded(e -> {
            resultVBox.getChildren().clear();
            if (!geoCoordinateService.getValue().isEmpty()) {
                    for (SearchCityResult result : geoCoordinateService.getValue()) {
                        Label label = new Label(result.getSearchResultDisplayText());
                        label.setMaxWidth(Double.MAX_VALUE);
                        label.getStyleClass().add("searching-result-label");
                        label.setOnMouseClicked(e1 -> {
                            weatherForecastManager.fetchWeather(result, weatherForecastVBox);
                            searchResultPane.setVisible(false);
                        });
                        resultVBox.getChildren().add(label);
                    }
                } else {
                Label label = new Label("No results");
                label.getStyleClass().add("info-label");
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
        setSearchButtons();
    }

    private void setSearchButtons() {
        currentLocationSearchButton.setGraphic(new ImageView(new Image(
                Launcher.class.getResourceAsStream("icons/world1.png"))));
        destinationSearchButton.setGraphic(new ImageView(new Image(
                Launcher.class.getResourceAsStream("icons/world2.png"))));
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