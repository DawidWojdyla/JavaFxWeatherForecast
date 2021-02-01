package it.dawidwojdyla.model;

import java.util.Objects;

/**
 * Created by Dawid on 2021-01-13.
 */
public class Weather {

    private final int minTemp;
    private final int maxTemp;
    private final String probabilityOfPrecipitation;
    private final String rain;
    private final String snow;
    private final String pressure;
    private final String humidity;
    private final String cloudiness;
    private final String windSpeed;
    private final String sunset;
    private final String sunrise;
    private final String description;
    private final String iconName;
    private final String date;

    public Weather(int minTemp, int maxTemp, String probabilityOfPrecipitation, String rain, String snow,
                   String pressure, String humidity, String cloudiness, String windSpeed, String sunset,
                   String sunrise, String description, String iconName, String date) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.probabilityOfPrecipitation = probabilityOfPrecipitation;
        this.rain = rain;
        this.snow = snow;
        this.pressure = pressure;
        this.humidity = humidity;
        this.cloudiness = cloudiness;
        this.windSpeed = windSpeed;
        this.sunset = sunset;
        this.sunrise = sunrise;
        this.description = description;
        this.iconName = iconName;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public String getProbabilityOfPrecipitation() {
        return probabilityOfPrecipitation;
    }

    public String getRain() {
        return rain;
    }

    public String getSnow() {
        return snow;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getCloudiness() {
        return cloudiness;
    }

    public String getSunset() {
        return sunset;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getDescription() {
        return description;
    }

    public String getIconName() {
        return iconName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return minTemp == weather.minTemp &&
                maxTemp == weather.maxTemp &&
                Objects.equals(probabilityOfPrecipitation, weather.probabilityOfPrecipitation) &&
                Objects.equals(rain, weather.rain) &&
                Objects.equals(snow, weather.snow) &&
                Objects.equals(pressure, weather.pressure) &&
                Objects.equals(humidity, weather.humidity) &&
                Objects.equals(cloudiness, weather.cloudiness) &&
                Objects.equals(windSpeed, weather.windSpeed) &&
                Objects.equals(sunset, weather.sunset) &&
                Objects.equals(sunrise, weather.sunrise) &&
                Objects.equals(description, weather.description) &&
                Objects.equals(iconName, weather.iconName) &&
                Objects.equals(date, weather.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minTemp, maxTemp, probabilityOfPrecipitation, rain, snow, pressure, humidity, cloudiness, windSpeed, sunset, sunrise, description, iconName, date);
    }

    @Override
    public String toString() {
        return "Weather{" +
                "minTemp=" + minTemp +
                ", maxTemp=" + maxTemp +
                ", probabilityOfPrecipitation='" + probabilityOfPrecipitation + '\'' +
                ", rain='" + rain + '\'' +
                ", snow='" + snow + '\'' +
                ", pressure='" + pressure + '\'' +
                ", humidity='" + humidity + '\'' +
                ", cloudiness='" + cloudiness + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", sunset='" + sunset + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", description='" + description + '\'' +
                ", iconName='" + iconName + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
