package it.dawidwojdyla;

import it.dawidwojdyla.view.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Dawid on 2021-01-11.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {

      WeatherForecastManager weatherForecastManager = new WeatherForecastManager();
      ViewFactory viewFactory = new ViewFactory(weatherForecastManager);
      viewFactory.showMainWindow();
    }

    public static void main(String[] args) {
        launch(args);
    }
}