package it.dawidwojdyla.controller.services;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by Dawid on 2021-01-16.
 */
public class JSONResponseFetcherOnHttpRequest {

    private String request;

    public JSONResponseFetcherOnHttpRequest(String request) {
        this.request = request;
    }

    public JSONObject getJSONResponse() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(request).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.connect();


            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine);
            }

            return new JSONObject(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}