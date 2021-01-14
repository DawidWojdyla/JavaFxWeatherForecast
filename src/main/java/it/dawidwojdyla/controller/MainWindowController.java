package it.dawidwojdyla.controller;

import it.dawidwojdyla.WeatherForecastManager;
import it.dawidwojdyla.view.ViewFactory;


/**
 * Created by Dawid on 2021-01-11.
 */
public class MainWindowController extends BaseController {

    public MainWindowController(WeatherForecastManager weatherForecastManager, ViewFactory viewFactory, String fxmlName) {
        super(weatherForecastManager, viewFactory, fxmlName);
    }

}
