package com.example.projekt.Controller;

import com.example.projekt.DTO.AverageValuesDto;
import com.example.projekt.Entity.Measurement;
import com.example.projekt.Service.CityService;
import com.example.projekt.Service.LoggerService;
import com.example.projekt.Service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
@ConditionalOnProperty(name = "readmode", havingValue = "false")
public class MeasurementMvcController {

    @Autowired
    private MeasurementService measurementService;
    private final LoggerService loggerService = new LoggerService();
    private static final String controllerName = "MeasurementMvcController";

    @GetMapping("/measurement/add")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public String addMeasurement(Model model) {
        loggerService.LogControlller(controllerName, "/measurement/add", new ArrayList<String>(Arrays.asList()));
        model.addAttribute("measurement", new Measurement());
        model.addAttribute("error", 0);
        return "measurement/add";
    }

    @PostMapping("/measurement/add")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public String insertMeasurement(@ModelAttribute Measurement measurement, Model model) {
        loggerService.LogControlller(controllerName, "/measurement/add", new ArrayList<String>(Arrays.asList(measurement.toString())));
        if(measurementService.Create(measurement)) {
            return "redirect:/measurement/list";
        }
        model.addAttribute("error", 1);
        return "measurement/add";
    }

    @GetMapping("/measurement/delete")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public String deleteMeasurement(@RequestParam Long id, Model model) {
        loggerService.LogControlller(controllerName, "/measurement/delete", new ArrayList<String>(Arrays.asList(id.toString())));
        measurementService.Delete(id);
        return "redirect:/measurement/list";
    }

    @GetMapping("/measurement/edit/{id}")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public String editMeasurement(@PathVariable Long id, Model model) {
        loggerService.LogControlller(controllerName, "/measurement/edit/{id}", new ArrayList<String>(Arrays.asList(id.toString())));
        model.addAttribute("measurement", measurementService.ReadById(id));
        model.addAttribute("error", 0);
        return "measurement/edit";
    }

    @PostMapping("/measurement/edit/{id}")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public String updateMeasurement(@PathVariable Long id, @ModelAttribute Measurement measurement, Model model) {
        loggerService.LogControlller(controllerName, "/measurement/edit/{id}", new ArrayList<String>(Arrays.asList(id.toString(), measurement.toString())));
        measurement.setId(id);
        if(measurementService.Update(measurement)) {
            return "redirect:/measurement/list";
        }
        model.addAttribute("error", 1);
        return "measurement/edit";
    }
}