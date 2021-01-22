package it.dawidwojdyla.controller;

import it.dawidwojdyla.Launcher;
import it.dawidwojdyla.WeatherForecastManager;
import it.dawidwojdyla.controller.services.FetchGeoCoordinatesService;
import it.dawidwojdyla.model.SearchCityResult;
import it.dawidwojdyla.model.WeatherConditionsOfTheLocation;
import it.dawidwojdyla.model.WeatherForecast;
import it.dawidwojdyla.model.constants.Constants;
import it.dawidwojdyla.view.WeatherDayViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.List;
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
            currentLocationInfoLabel.setText(Constants.VALIDATION_ERROR_MESSAGE);
        }
    }

    @FXML
    void destinationSearchButtonAction() {
        destinationInfoLabel.setText("");
        if (destinationTextField.getText().length() > 2) {
            fetchGeoCoordinates(destinationTextField.getText(), destinationSearchResultVBox,
                    destinationWeatherVBox, destinationSearchResultPane);
        } else {
            destinationInfoLabel.setText(Constants.VALIDATION_ERROR_MESSAGE);
        }
    }

    private void fetchGeoCoordinates(String searchText, VBox resultVBox, VBox weatherForecastVBox, AnchorPane searchResultPane) {
        FetchGeoCoordinatesService geoCoordinateService = new FetchGeoCoordinatesService(searchText);
        geoCoordinateService.setOnSucceeded(e -> {
            if (geoCoordinateService.getValue().isEmpty()) {
                AnchorPane parentPane = (AnchorPane) searchResultPane.getParent();
                Label label = (Label) parentPane.getChildren().get(0);
                label.setText("No results");
            } else {
                showSearchCityResults(resultVBox, weatherForecastVBox, searchResultPane, geoCoordinateService.getValue());
            }
        });
        geoCoordinateService.start();
    }

    private void showSearchCityResults(VBox resultVBox, VBox weatherForecastVBox, AnchorPane searchResultPane, List<SearchCityResult> searchCityResultList) {
        resultVBox.getChildren().clear();
        for (SearchCityResult result: searchCityResultList) {
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        weatherForecastManager.setMainWindowController(this);
        weatherForecastManager.fetchDefaultLocationsWeather(currentLocationWeatherVBox, destinationWeatherVBox);

        setSearchButtons();

        currentLocationTextField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                currentLocationSearchButtonAction();
            }
        });
        destinationTextField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                destinationSearchButtonAction();
            }
        });
    }

    private void setSearchButtons() {
        currentLocationSearchButton.setGraphic(new ImageView(new Image(
                Launcher.class.getResourceAsStream("icons/loupe_home.png"))));
        destinationSearchButton.setGraphic(new ImageView(new Image(
                Launcher.class.getResourceAsStream("icons/loupe_plane.png"))));
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