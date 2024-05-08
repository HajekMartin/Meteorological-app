package com.example.projekt.Service;

import com.example.projekt.Entity.Measurement;
import com.example.projekt.Repository.CityRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONObject;

@Component
public class OpenWeatherService {

    @Value("${apiKey}")
    private String apiKey;

    @Autowired
    private CityRepository cityRepository;


    // https://api.openweathermap.org/data/2.5/weather?id={city id}&appid={API key}
    // Nechci mít v db město co nepůjde aktualizovat z openweather - řeším pouze id - jméno mi je jedno tak pošlu dotaz zda jde pro toto město stáhnout něco
    public boolean TestCityId(Long cityId) {
        try {
            String apiUrl = String.format("https://api.openweathermap.org/data/2.5/weather?id=%d&appid=%s&units=%s", cityId, apiKey, "metric");
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            connection.disconnect();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Measurement DownloadActualMeasurement(Long cityId) {
        try {
            String apiUrl = String.format("https://api.openweathermap.org/data/2.5/weather?id=%d&appid=%s&units=%s", cityId, apiKey, "metric");
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                JSONObject jsonResponse = new JSONObject(response.toString());
                Measurement measurement = new Measurement();
                measurement.setCity(cityRepository.findCityById(cityId));
                measurement.setTemperature(jsonResponse.getJSONObject("main").getDouble("temp"));
                measurement.setDate(jsonResponse.getLong("dt"));
                if (jsonResponse.has("rain") && jsonResponse.getJSONObject("rain").has("1h")) {
                    measurement.setRain(jsonResponse.getJSONObject("rain").getDouble("1h"));
                }
                else {
                    measurement.setRain(0d);
                }
                measurement.setWindSpeed(jsonResponse.getJSONObject("wind").getDouble("speed"));
                connection.disconnect();
                return  measurement;
            }
            connection.disconnect(); // Pokud není ok prostě ukončím a metoda doběhne a vrátí null
        } catch (IOException e) {
            // Hození vyjímky mi nijak nepomůže a navíc by mi skončilo vlákno
        } catch (JSONException e) { // Pokud by byl JSON nevalidní celkově
            // Hození vyjímky mi nijak nepomůže a navíc by mi skončilo vlákno
        }
        return null;
    }
}
