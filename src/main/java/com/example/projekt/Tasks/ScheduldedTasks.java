package com.example.projekt.Tasks;

import com.example.projekt.Entity.Measurement;
import com.example.projekt.Service.CityService;
import com.example.projekt.Service.LoggerService;
import com.example.projekt.Service.MeasurementService;
import com.example.projekt.Service.OpenWeatherService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
@ConditionalOnProperty(name = "readmode", havingValue = "false")
public class ScheduldedTasks {

    @Autowired
    private OpenWeatherService openWeatherService;
    @Autowired
    private LoggerService loggerService;
    private final String serviceName = "ScheduldedTasks";

    @Value("${expirationDays}")
    private Long expirationDays;

    @Value("${minuteApiLimit}")
    private Long minuteApiLimit;

    @Value("${apiRefreshRateInSeconds}")
    private Long apiRefreshRateInSeconds;

    @Autowired
    private MeasurementService measurementService;
    @Autowired
    private CityService cityService;

    private Long countedRefreshRate;

    @Scheduled(cron="0 0 0 * * *") // Každou půlnoc se smažou staré záznamy
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public void DeleteOldMeasurements() {
        measurementService.DeleteOldMeasurements();
    }

    @PostConstruct
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public void init() {
        // Spustím vlákno které bude stahovat data z api
        loggerService.LogService(serviceName, "init", new ArrayList<String>(Arrays.asList()));
        countedRefreshRate = (apiRefreshRateInSeconds > 1 ? apiRefreshRateInSeconds : 1)*1000;
        if(countedRefreshRate < 1000) countedRefreshRate = 1000l;
        loggerService.LogService(serviceName, "init2", new ArrayList<String>(Arrays.asList(countedRefreshRate.toString())));
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                UpdateDataFromApi();
            }
        });
        t.start();
    }

    public void UpdateDataFromApi() {
        // Nekonečný abych furt stahoval nová data
        while(true) {
            Long cityId = cityService.CityIdWithOldestData();
            Measurement measurement = openWeatherService.DownloadActualMeasurement(cityId);
            if (measurement != null) { // Pokud je někde problém vrací null
                if(measurementService.Create(measurement)){
                    System.out.println("Completed update city " + measurement.getCity().getName() + " from API");
                    loggerService.LogService(serviceName, "UpdateDataFromApi", new ArrayList<String>(Arrays.asList(("Completed update city " + measurement.getCity().getName() + " from API"))));
                } else {
                    System.out.println("Failed to update city " + measurement.getCity().getName() + " from API");
                    loggerService.LogService(serviceName, "UpdateDataFromApi", new ArrayList<String>(Arrays.asList(("Failed to update city " + measurement.getCity().getName() + " from API"))));
                }
            }else {
                System.out.println("Failed to update city " + cityId + " from API");
                loggerService.LogService(serviceName, "UpdateDataFromApi", new ArrayList<String>(Arrays.asList(("Failed to update city " + cityId + " from API"))));
            }

            try {
                Thread.sleep(countedRefreshRate);
            } catch (InterruptedException e) {
                loggerService.LogService(serviceName, "UpdateDataFromApi", new ArrayList<String>(Arrays.asList()));
            }
        }
    }
}
