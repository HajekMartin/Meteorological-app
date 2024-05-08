package com.example.projekt.Controller;

import com.example.projekt.Service.CityService;
import com.example.projekt.Service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class CityMvcReadController {

    @Autowired
    private CityService cityService;
    private final LoggerService loggerService = new LoggerService();
    private static final String controllerName = "CityMvcReadController";

    @GetMapping("/city/list")
    public String getCities(Model model) {
        loggerService.LogControlller(controllerName, "/city/list", new ArrayList<String>(Arrays.asList()));
        model.addAttribute("cities", cityService.ReadAll());
        return "city/list";
    }
}
