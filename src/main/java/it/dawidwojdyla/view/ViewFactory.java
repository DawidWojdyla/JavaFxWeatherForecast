package it.dawidwojdyla.view;

import it.dawidwojdyla.Main;
import it.dawidwojdyla.WeatherForecastManager;
import it.dawidwojdyla.controller.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Dawid on 2021-01-11.
 */
public class ViewFactory {

    private final WeatherForecastManager weatherForecastManager;

    public ViewFactory(WeatherForecastManager weatherForecastManager) {
        this.weatherForecastManager = weatherForecastManager;
    }

    public void showMainWindow() {
        MainWindowController mainWindowController = new MainWindowController(weatherForecastManager);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/MainWindow.fxml"));
        fxmlLoader.setController(mainWindowController);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(parent);
        scene.getStylesheets().add(Main.class.getResource("css/style.css").toExternalForm());
        Stage stage = new Stage();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("icons/app-icon.png")));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
