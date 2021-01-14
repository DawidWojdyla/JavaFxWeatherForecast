package it.dawidwojdyla.model;

/**
 * Created by Dawid on 2021-01-13.
 */
public class WeatherForecast {

    float minTemp;
    float maxTemp;
    float probabilityOfPrecipation;
    float rain;
    float snow;
    int pressure;
    int humidity;
    int clouds;
    long sunset;
    long surise;
    String description;
    String iconName;

    public WeatherForecast() {

    }

    public float getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp;
    }

    public float getProbabilityOfPrecipation() {
        return probabilityOfPrecipation;
    }

    public void setProbabilityOfPrecipation(float probabilityOfPrecipation) {
        this.probabilityOfPrecipation = probabilityOfPrecipation;
    }

    public float getRain() {
        return rain;
    }

    public void setRain(float rain) {
        this.rain = rain;
    }

    public float getSnow() {
        return snow;
    }

    public void setSnow(float snow) {
        this.snow = snow;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public long getSurise() {
        return surise;
    }

    public void setSurise(long surise) {
        this.surise = surise;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
