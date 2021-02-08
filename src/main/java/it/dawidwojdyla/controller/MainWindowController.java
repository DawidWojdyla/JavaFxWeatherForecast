package it.dawidwojdyla.controller;

import it.dawidwojdyla.Main;
import it.dawidwojdyla.WeatherForecastManager;
import it.dawidwojdyla.controller.services.FetchGeoCoordinatesService;
import it.dawidwojdyla.controller.services.FetchWeatherService;
import it.dawidwojdyla.model.SearchCityResult;
import it.dawidwojdyla.model.WeatherConditionsOfTheLocation;
import it.dawidwojdyla.model.Weather;
import it.dawidwojdyla.model.Constants;
import it.dawidwojdyla.view.WeatherDayViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

    private final WeatherForecastManager weatherForecastManager;
    private final FetchGeoCoordinatesService geoCoordinateService;
    private FetchGeoCoordinatesService geoCoordinateServiceSpare;

    public MainWindowController(WeatherForecastManager weatherForecastManager) {
        this.weatherForecastManager = weatherForecastManager;
        geoCoordinateService = new FetchGeoCoordinatesService();
        //geoCoordinateServiceSpare = new FetchGeoCoordinatesService();

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
        fetchGeoCoordinates(currentLocationTextField.getText(),
                currentLocationSearchResultVBox, currentLocationWeatherVBox, currentLocationSearchResultPane);
    }

    @FXML
    void destinationSearchButtonAction() {
        destinationInfoLabel.setText("");
        fetchGeoCoordinates(destinationTextField.getText(), destinationSearchResultVBox,
                destinationWeatherVBox, destinationSearchResultPane);
    }

    private void fetchGeoCoordinates(String searchText, VBox resultVBox, VBox weatherVBox, AnchorPane resultPane) {
        if (searchText.length() <= Constants.MINIMUM_SEARCH_TEXT_VALIDATION_LENGTH) {
            showMessage(weatherVBox, Constants.TEXTFIELD_VALIDATION_ERROR_MESSAGE);
        } else {
            startNewFetchGeoService(setGeoCoordinateService(), searchText, resultVBox, weatherVBox, resultPane);
        }
    }

    private FetchGeoCoordinatesService setGeoCoordinateService() {
        if (!geoCoordinateService.isRunning()) {
            return geoCoordinateService;
        } else if (geoCoordinateServiceSpare == null) {
            return geoCoordinateServiceSpare = new FetchGeoCoordinatesService();
        } else if (!geoCoordinateServiceSpare.isRunning()){
            return geoCoordinateServiceSpare;
        } else {
            return new FetchGeoCoordinatesService();
        }
    }

    private void startNewFetchGeoService(FetchGeoCoordinatesService geoCoordinateService, String searchText,
                                         VBox resultVBox, VBox weatherVBox, AnchorPane resultPane) {
        geoCoordinateService.setSearchingPlaceName(searchText);
        geoCoordinateService.setOnSucceeded(e -> {
            List<SearchCityResult> result = geoCoordinateService.getValue();
            if (result == null) {
                showMessage(resultPane, Constants.CONNECTION_FAILED_MESSAGE);
            } else if (result.isEmpty()) {
                showMessage(resultPane, Constants.NO_SEARCH_CITY_RESULT_MESSAGE);
            } else {
                showSearchResult(resultVBox, weatherVBox, resultPane, result);
            }
        });
        geoCoordinateService.setOnFailed(e -> showMessage(weatherVBox, Constants.CONNECTION_FAILED_MESSAGE));
        geoCoordinateService.restart();
    }

    public void showMessage(Parent mainAnchorPaneChild, String message) {
        AnchorPane parentPane = (AnchorPane) mainAnchorPaneChild.getParent();
        Label label = (Label) parentPane.getChildren().get(0);
        label.setText(message);
    }

    private void showSearchResult(VBox resultVBox, VBox weatherVBox, AnchorPane searchResultPane,
                                  List<SearchCityResult> searchCityResultList) {
        resultVBox.getChildren().clear();
        for (SearchCityResult result : searchCityResultList) {
            Label label = new Label(result.getSearchResultDisplayText());
            label.setMaxWidth(Double.MAX_VALUE);
            label.getStyleClass().add("search-result-label");
            label.setOnMouseClicked(e -> {
                weatherForecastManager.fetchWeather(result, weatherVBox);
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
        setEnterPressedListeners();
    }

    private void setEnterPressedListeners() {
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
                Main.class.getResourceAsStream("icons/loupe_home.png"))));
        destinationSearchButton.setGraphic(new ImageView(new Image(
                Main.class.getResourceAsStream("icons/loupe_plane.png"))));
    }

    public void showWeather(WeatherConditionsOfTheLocation weatherConditionsOfTheLocation, VBox weatherVBox) {
        weatherVBox.getChildren().clear();
        if (weatherConditionsOfTheLocation != null) {
            Label placeNameLabel = new Label(weatherConditionsOfTheLocation.getPlaceName());
            placeNameLabel.getStyleClass().add("place-name-label");
            weatherVBox.getChildren().add(placeNameLabel);
            for (Weather weather : weatherConditionsOfTheLocation.getWeatherList()) {
                weatherVBox.getChildren().add(WeatherDayViewFactory.createWeatherDayAnchorPane(weather));
            }
        } else {
            showMessage(weatherVBox, Constants.CONNECTION_FAILED_MESSAGE);
        }
    }
}