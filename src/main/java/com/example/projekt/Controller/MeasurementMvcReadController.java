package com.example.projekt.Controller;

import com.example.projekt.DTO.AverageValuesDto;
import com.example.projekt.Service.CityService;
import com.example.projekt.Service.LoggerService;
import com.example.projekt.Service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class MeasurementMvcReadController {

    @Autowired
    private MeasurementService measurementService;
    @Autowired
    private CityService cityService;
    private final LoggerService loggerService = new LoggerService();
    private static final String controllerName = "MeasurementMvcReadController";

    @GetMapping("/measurement/list")
    public String getMeasurements(@RequestParam(required = false) Long cityId, @RequestParam(required = false) Long duration, Model model) {
        loggerService.LogControlller(controllerName, "/measurement/list", new ArrayList<String>(Arrays.asList((cityId == null ? "" : cityId.toString()), (duration == null ? "" : cityId.toString()))));
        model.addAttribute("cities", cityService.ReadAll());
        if (cityId != null && duration != null) {
            AverageValuesDto averageValuesDto = measurementService.AverageValues(cityId, duration);
            model.addAttribute("measurements", measurementService.ReadForCityIdAndDuration(cityId, duration));
            model.addAttribute("avgMeasurements",averageValuesDto);
        }
        return "measurement/list";
    }


}
