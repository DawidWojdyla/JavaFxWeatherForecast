package it.dawidwojdyla.controller.services;

import it.dawidwojdyla.model.SearchCityResult;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dawid on 2021-01-16.
 */
public class FetchGeoCoordinatesService extends Service<List<SearchCityResult>> {

    private final String GEOAPIFY_HOST = "https://api.geoapify.com/v1/geocode/search";
    private final String API_KEY = "d499b25463e34a59b66068281d8ebd39";
    private String searchingPlaceName;
    private List<SearchCityResult> cities = new ArrayList<>();
    String lastLongLat;

    public FetchGeoCoordinatesService(String searchingPlaceName) {
        this.searchingPlaceName = searchingPlaceName;
    }

    private String buildRequest() {
        return GEOAPIFY_HOST + "?text=" + URLEncoder.encode(searchingPlaceName, StandardCharsets.UTF_8) +
                "&type=city&result_type=city&limit=10" +"&apiKey=" + API_KEY;
    }

    @Override
    protected Task<List<SearchCityResult>> createTask() {
        return new Task<>() {
            @Override
            protected List<SearchCityResult> call() {
                lastLongLat = "";
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

        JSONObject properties = (JSONObject) jsonCityResult.get("properties");

        if (!lastLongLat.equals(properties.optString("lat") + properties.optString("lon"))) {
            lastLongLat = properties.optString("lat") + properties.optString("lon");

            SearchCityResult result = new SearchCityResult(properties.optString("lat"),
                    properties.optString("lon"), properties.optString("city"), properties.optString("country"));

            result.setState(properties.optString("state", ""));
            result.setPostCode(properties.optString("postcode", ""));
            result.setCounty(properties.optString("county", ""));
            result.setMunicipality(properties.optString("municipality", ""));

            cities.add(result);
        }
    }
}
