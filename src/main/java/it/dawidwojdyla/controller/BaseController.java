package it.dawidwojdyla.controller;

import it.dawidwojdyla.WeatherForecastManager;
import it.dawidwojdyla.view.ViewFactory;

/**
 * Created by Dawid on 2021-01-11.
 */
public abstract class BaseController {

    private WeatherForecastManager weatherForecastManager;
    private ViewFactory viewFactory;
    private String fxmlName;

    public BaseController(WeatherForecastManager weatherForecastManager, ViewFactory viewFactory, String fxmlName) {
        this.weatherForecastManager = weatherForecastManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
