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

import static it.dawidwojdyla.model.Constants.GEOAPIFY_API_HOST;
import static it.dawidwojdyla.model.Constants.GEOAPIFY_API_KEY;

/**
 * Created by Dawid on 2021-01-16.
 */
public class FetchGeoCoordinatesService extends Service<List<SearchCityResult>> {

    private String searchingPlaceName;
    private String lastLongLat;

    public void setSearchingPlaceName(String searchingPlaceName) {
        this.searchingPlaceName = searchingPlaceName;
    }

    @Override
    protected Task<List<SearchCityResult>> createTask() {
        return new Task<>() {
            @Override
            protected List<SearchCityResult> call() {
                List<SearchCityResult> resultList = new ArrayList<>();
                HttpRequestManager httpRequestManager = new HttpRequestManager(buildRequest());
                JSONObject searchResult = httpRequestManager.getJSONResponse();
                if (searchResult != null) {
                    lastLongLat = "";
                    JSONArray features = searchResult.getJSONArray("features");
                    for (int i = 0; i < features.length(); i++) {
                        JSONObject jsonCityResult = features.getJSONObject(i);
                        SearchCityResult cityResult = handleCityResult(jsonCityResult);
                        if (cityResult != null) {
                            resultList.add(cityResult);
                        }
                    }
                    return resultList;
                }
                return null;
            }
        };
    }

    private String buildRequest() {
        return GEOAPIFY_API_HOST + "?text=" + URLEncoder.encode(searchingPlaceName, StandardCharsets.UTF_8) +
                "&type=city&result_type=city&limit=10" + "&apiKey=" + GEOAPIFY_API_KEY;
    }

    private SearchCityResult handleCityResult(JSONObject jsonCityResult) {

        JSONObject properties = jsonCityResult.getJSONObject("properties");

        if (!lastLongLat.equals(properties.optString("lat") + properties.optString("lon"))) {
            lastLongLat = properties.optString("lat") + properties.optString("lon");

            SearchCityResult result = new SearchCityResult(properties.optString("lat"),
                    properties.optString("lon"), properties.optString("city"), properties.optString("country"));

            result.setState(properties.optString("state", ""));
            result.setPostCode(properties.optString("postcode", ""));
            result.setCounty(properties.optString("county", ""));
            result.setMunicipality(properties.optString("municipality", ""));

            return result;
        }
        return  null;
    }
}
