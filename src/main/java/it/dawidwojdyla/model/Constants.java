package it.dawidwojdyla.model;

/**
 * Created by Dawid on 2021-01-22.
 */
public class Constants {

    public static final String CURRENT_LOCATION_LATITUDE_DEFAULT = "50.0412";
    public static final String CURRENT_LOCATION_LONGITUDE_DEFAULT = "21.9991";
    public static final String CURRENT_LOCATION_CITY_DEFAULT = "Rzeszów";
    public static final String CURRENT_LOCATION_COUNTRY_DEFAULT = "Poland";
    public static final String DESTINATION_LATITUDE_DEFAULT = "45.4420043";
    public static final String DESTINATION_LONGITUDE_DEFAULT = "12.3378095";
    public static final String DESTINATION_CITY_DEFAULT = "Venice";
    public static final String DESTINATION_COUNTRY_DEFAULT = "Italy";

    public static final String OPEN_WEATHER_ONE_CALL_API_HOST = "https://api.openweathermap.org/data/2.5/onecall";
    public static final String OPEN_WEATHER_ONE_CALL_API_KEY = "39af7a169432c32cc8f937e351c91f46";

    public static  final String GEOAPIFY_API_HOST = "https://api.geoapify.com/v1/geocode/search";
    public static  final String GEOAPIFY_API_KEY = "d499b25463e34a59b66068281d8ebd39";

    public static final String TEXTFIELD_VALIDATION_ERROR_MESSAGE = "Enter at least characters";
    public static final int MINIMUM_SEARCH_TEXT_VALIDATION_LENGTH = 3;
    public static final String NO_SEARCH_CITY_RESULT_MESSAGE = "No such place was found";
    public static final String CONNECTION_FAILED_MESSAGE = "Service temporarily unavailable";

    public static final int HTTP_REQUEST_CONNECT_RETRIES_NUMBER = 2;
    public static final int MAX_WEATHER_DAY_AMOUNT = 5;
}
