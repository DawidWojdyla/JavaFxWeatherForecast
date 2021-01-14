package it.dawidwojdyla.view;

import it.dawidwojdyla.Launcher;
import it.dawidwojdyla.WeatherForecastManager;
import it.dawidwojdyla.controller.BaseController;
import it.dawidwojdyla.controller.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Dawid on 2021-01-11.
 */
public class ViewFactory {

    private WeatherForecastManager weatherForecastManager;

    public ViewFactory(WeatherForecastManager weatherForecastManager) {
        this.weatherForecastManager = weatherForecastManager;
    }

    public void showMainWindow() {
        BaseController mainWindowController = new MainWindowController(weatherForecastManager, this, "MainWindow");
        initializeStage(mainWindowController);
    }

    public void initializeStage(BaseController controller) {

        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("fxml/" + controller.getFxmlName() + ".fxml"));
        fxmlLoader.setController(controller);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
