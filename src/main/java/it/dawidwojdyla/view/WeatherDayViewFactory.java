package it.dawidwojdyla.view;

import it.dawidwojdyla.Launcher;
import it.dawidwojdyla.model.WeatherForecast;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * Created by Dawid on 2021-01-21.
 */
public class WeatherDayViewFactory {

    public static AnchorPane createWeatherDayAnchorPane(WeatherForecast weatherForecast) {
        AnchorPane anchorPane = new AnchorPane();

        Label date = new Label(weatherForecast.getDate());
        AnchorPane.setTopAnchor(date, 1.0);
        AnchorPane.setLeftAnchor(date, 2.0);
        date.setStyle("-fx-font-size: 15px");

        Label description = new Label(weatherForecast.getDescription().substring(0, 1).toUpperCase()
                + weatherForecast.getDescription().substring(1));
        AnchorPane.setTopAnchor(description, 20.0);
        AnchorPane.setLeftAnchor(description, 2.0);

        Label temperature = new Label(
                weatherForecast.getMaxTemp() + "ºC / " + weatherForecast.getMinTemp() + "ºC");
        AnchorPane.setBottomAnchor(temperature, -1.0);
        AnchorPane.setRightAnchor(temperature, 3.0);
        temperature.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label tempDescription = new Label("max  /  min");
        AnchorPane.setBottomAnchor(tempDescription, 25.0);
        AnchorPane.setRightAnchor(tempDescription, 15.0);

        Image mainIcon = new Image(Launcher.class.getResourceAsStream
                ("icons/" + weatherForecast.getIconName() + "@2x.png"));
        ImageView mainImageView = new ImageView(mainIcon);
        AnchorPane.setTopAnchor(mainImageView,-20.0);
        AnchorPane.setRightAnchor(mainImageView,2.0);


        anchorPane.getChildren().addAll(date, setTopIconsHBox(weatherForecast), setBottomIconsHBox(weatherForecast),
                description, temperature, mainImageView, tempDescription);
        anchorPane.setMinSize(100,100);
        anchorPane.getStyleClass().add("weather-item-pane");
        anchorPane.setStyle("-fx-background-color: linear-gradient(to right, "
                + RGBColorGenerator.generateColorFromTemperature(weatherForecast.getMaxTemp()) +
                ", " + RGBColorGenerator.generateColorFromTemperature(weatherForecast.getMinTemp()) + ")");

        return anchorPane;
    }

    private static HBox setTopIconsHBox(WeatherForecast weatherForecast) {

        Image sun = new Image(Launcher.class.getResourceAsStream
                ("icons/sun_small.png"));
        ImageView sunImageView = new ImageView(sun);
        Label sunriseLabel = new Label(weatherForecast.getSunrise());
        HBox sunriseHBox = new HBox(sunImageView, sunriseLabel);
        sunriseHBox.setSpacing(3);

        Image moon = new Image(Launcher.class.getResourceAsStream
                ("icons/moon_small.png"));
        ImageView moonImageView = new ImageView(moon);
        Label sunset = new Label(weatherForecast.getSunset());
        HBox sunsetHBox = new HBox(moonImageView, sunset);

        HBox topIconsHBox = new HBox(sunriseHBox, sunsetHBox);
        topIconsHBox.setSpacing(5);
        AnchorPane.setTopAnchor(topIconsHBox, 2.0);
        AnchorPane.setLeftAnchor(topIconsHBox, 135.0);

        return topIconsHBox;
    }

    private static HBox setBottomIconsHBox(WeatherForecast weatherForecast) {

        Image probabilityOfPrecipitationIcon = new Image(Launcher.class.getResourceAsStream
                ("icons/umbrella_small.png"));
        ImageView probabilityOfPrecipitationIconImageView = new ImageView(probabilityOfPrecipitationIcon);
        Label popLabel = new Label(weatherForecast.getProbabilityOfPrecipitation());
        HBox popHBox = new HBox(probabilityOfPrecipitationIconImageView, popLabel);
        popHBox.setSpacing(2);

        Image pressureIcon = new Image(Launcher.class.getResourceAsStream
                ("icons/pressure_small.png"));
        ImageView pressureImageView = new ImageView(pressureIcon);
        Label pressureLabel = new Label(weatherForecast.getPressure() + "hPa");
        HBox pressureHBox = new HBox(pressureImageView, pressureLabel);
        pressureHBox.setSpacing(2);

        HBox bottomIconsHBox = new HBox(popHBox, pressureHBox);
        bottomIconsHBox.setSpacing(10);
        AnchorPane.setBottomAnchor(bottomIconsHBox, 1.0);
        AnchorPane.setLeftAnchor(bottomIconsHBox, 2.0);

        return bottomIconsHBox;
    }
}
