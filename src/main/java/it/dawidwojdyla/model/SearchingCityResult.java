package it.dawidwojdyla.model;


/**
 * Created by Dawid on 2021-01-16.
 */
public class SearchingCityResult {

    private String cityName;
    private String latitude;
    private String longitude;
    private String country;
    private String postCode;
    private String state;
    private String county;
    private String municipality;

    public SearchingCityResult() {
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDisplayName() {
        String displayName = cityName + " ";

        if (!postCode.equals("")) {
            displayName += "[" + postCode + "]";
        }

        if (!municipality.equals("")) {
            displayName += "[" + municipality + "]";
        }

        if (!county.equals("")) {
            displayName += "[" + county + "]";
        }

        if (!state.equals("")) {
            displayName += "[" + state + "]";
        }

        displayName += "[" + country + "]";

        return displayName;
    }


}
