package it.dawidwojdyla.controller.services;

import it.dawidwojdyla.model.SearchingCityResult;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dawid on 2021-01-16.
 */
public class FetchGeoCoordinatesService extends Service<List<SearchingCityResult>> {

    private final String GEOAPIFY_HOST = "https://api.geoapify.com/v1/geocode/search";
    private final String API_KEY = "d499b25463e34a59b66068281d8ebd39";
    private String searchingPlaceName;
    private List<SearchingCityResult> cities = new ArrayList<>();

    public FetchGeoCoordinatesService(String searchingPlaceName) {
        this.searchingPlaceName = searchingPlaceName;
    }

    private String buildRequest() {
        return GEOAPIFY_HOST + "?text=" + searchingPlaceName +
                "&type=city&bias=countrycode:none" +"&apiKey=" + API_KEY;
    }

    @Override
    protected Task<List<SearchingCityResult>> createTask() {
        return new Task<>() {
            @Override
            protected List<SearchingCityResult> call() {

                JSONResponseFetcherOnHttpRequest responseFetcher = new JSONResponseFetcherOnHttpRequest(buildRequest());
                JSONObject searchResult = responseFetcher.getJSONResponse();
                JSONArray features = searchResult.getJSONArray("features");
                for (int i = 0; i < features.length(); i++) {
                    JSONObject jsonCityResult = (JSONObject) features.opt(i);
                    handleCityResult(jsonCityResult);
                }
                return cities;
            }
        };
    }

    private void handleCityResult(JSONObject jsonCityResult) {
        SearchingCityResult cityResult = new SearchingCityResult();

        JSONObject cityProperties = (JSONObject) jsonCityResult.get("properties");
        cityResult.setLongitude(cityProperties.optString("long"));
        cityResult.setLatitude(cityProperties.optString("lat"));
        cityResult.setCountry(cityProperties.optString("country"));
        cityResult.setCityName(cityProperties.optString("city"));
        cityResult.setState(cityProperties.optString("state", ""));
        cityResult.setPostCode(cityProperties.optString("postcode", ""));
        cityResult.setCounty(cityProperties.optString("county", ""));
        cityResult.setMunicipality(cityProperties.optString("municipality", ""));

        cities.add(cityResult);
    }
}
