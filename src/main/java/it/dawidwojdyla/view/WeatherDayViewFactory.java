package it.dawidwojdyla.view;

import it.dawidwojdyla.Main;
import it.dawidwojdyla.model.Weather;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * Created by Dawid on 2021-01-21.
 */
public class WeatherDayViewFactory {

    public static AnchorPane createWeatherDayAnchorPane(Weather weather) {

        Label date = new Label(weather.getDate());
        AnchorPane.setTopAnchor(date, 1.0);
        AnchorPane.setLeftAnchor(date, 2.0);
        date.getStyleClass().add("text-medium");

        Label description = new Label(weather.getDescription());
        AnchorPane.setTopAnchor(description, 20.0);
        AnchorPane.setLeftAnchor(description, 2.0);
        description.getStyleClass().add("weather-description");

        Label temperature = new Label(
                weather.getMaxTemp() + "ºC / " + weather.getMinTemp() + "ºC");
        AnchorPane.setBottomAnchor(temperature, -1.0);
        AnchorPane.setRightAnchor(temperature, 3.0);
        temperature.getStyleClass().add("temperature");

        Label tempDescription = new Label("max  /  min");
        AnchorPane.setBottomAnchor(tempDescription, 25.0);
        AnchorPane.setRightAnchor(tempDescription, 15.0);

        ImageView mainIcon = new ImageView(new Image(Main.class.getResourceAsStream
                ("icons/" + weather.getIconName() + "@2x.png")));
        AnchorPane.setTopAnchor(mainIcon, -20.0);
        AnchorPane.setRightAnchor(mainIcon, 2.0);

        AnchorPane anchorPane = new AnchorPane(date, setTopIconsHBox(weather), setBottomIconsHBox(weather),
                description, temperature, mainIcon, tempDescription);
        anchorPane.setMinSize(100, 100);
        anchorPane.getStyleClass().add("weather-item-pane");
        anchorPane.setStyle("-fx-background-color: linear-gradient(to right, "
                + RGBColorGenerator.generateCssStyleColorFromTemperature(weather.getMaxTemp()) +
                ", " + RGBColorGenerator.generateCssStyleColorFromTemperature(weather.getMinTemp()) + ")");

        return anchorPane;
    }

    private static HBox setTopIconsHBox(Weather weather) {

        ImageView sunIcon = new ImageView(new Image(
                Main.class.getResourceAsStream("icons/sun_small.png")));
        Label sunriseLabel = new Label(weather.getSunrise());
        HBox sunriseHBox = new HBox(sunIcon, sunriseLabel);
        sunriseHBox.setSpacing(3);

        ImageView moonIcon = new ImageView(new Image(
                Main.class.getResourceAsStream("icons/moon_small.png")));
        Label sunset = new Label(weather.getSunset());
        HBox sunsetHBox = new HBox(moonIcon, sunset);

        HBox topIconsHBox = new HBox(sunriseHBox, sunsetHBox);
        topIconsHBox.setSpacing(10);
        AnchorPane.setTopAnchor(topIconsHBox, 2.0);
        AnchorPane.setLeftAnchor(topIconsHBox, 135.0);

        return topIconsHBox;
    }

    private static HBox setBottomIconsHBox(Weather weather) {

        ImageView probabilityOfPrecipitationIcon = new ImageView(
                new Image(Main.class.getResourceAsStream("icons/umbrella_small.png")));
        Label probabilityOfPrecipitation = new Label(weather.getProbabilityOfPrecipitation());
        HBox probabilityOfPrecipitationHBox = new HBox(probabilityOfPrecipitationIcon, probabilityOfPrecipitation);
        probabilityOfPrecipitationHBox.setSpacing(2);

        ImageView pressureIcon = new ImageView(
                new Image(Main.class.getResourceAsStream("icons/pressure_small.png")));
        Label pressure = new Label(weather.getPressure() + "mb");
        HBox pressureHBox = new HBox(pressureIcon, pressure);
        pressureHBox.setSpacing(2);

        HBox bottomIconsHBox = new HBox(probabilityOfPrecipitationHBox, pressureHBox);
        bottomIconsHBox.setSpacing(10);
        AnchorPane.setBottomAnchor(bottomIconsHBox, 1.0);
        AnchorPane.setLeftAnchor(bottomIconsHBox, 2.0);

        return bottomIconsHBox;
    }
}