package it.dawidwojdyla.model;


/**
 * Created by Dawid on 2021-01-16.
 */
public class SearchCityResult {

    private String city;
    private String latitude;
    private String longitude;
    private String country;
    private String postCode;
    private String state;
    private String county;
    private String municipality;

    public SearchCityResult(String latitude, String longitude, String city, String country) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.country = country;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
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

    public void setCounty(String county) {
        this.county = county;
    }

    public String getSearchResultDisplayText() {
        String displayText = city + " ";

        displayText += addToDisplayTextIfNotEmpty(postCode);

        displayText += addToDisplayTextIfNotEmpty(municipality);

        displayText += addToDisplayTextIfNotEmpty(county);

        displayText += addToDisplayTextIfNotEmpty(state);

        displayText += "[" + country + "]";

        return displayText;
    }

    private String addToDisplayTextIfNotEmpty(String text) {
        if (!text.equals("")) {
            return "[" + text + "]";
        }
        return "";
    }

    public String getDisplayName() {
        return city + ", " + country;
    }
}
