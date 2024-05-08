package com.example.projekt.Controller;

import com.example.projekt.Entity.City;
import com.example.projekt.Service.CityService;
import com.example.projekt.Service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
@ConditionalOnProperty(name = "readmode", havingValue = "false")
public class CityMvcController {

    @Autowired
    private CityService cityService;
    private final LoggerService loggerService = new LoggerService();
    private static final String controllerName = "CityMvcController";

    @GetMapping("/city/add")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public String addCity(Model model) {
        loggerService.LogControlller(controllerName, "/city/add", new ArrayList<String>(Arrays.asList()));
        model.addAttribute("city", new City());
        model.addAttribute("error", 0);
        return "city/add";
    }

    @PostMapping("/city/add")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public String insertCity(@ModelAttribute City city, Model model) {
        loggerService.LogControlller(controllerName, "/city/add", new ArrayList<String>(Arrays.asList(city.toString())));
        if (cityService.Create(city)) {
            return "redirect:/city/list";
        }
        model.addAttribute("error", 1);
        return "city/add";
    }

    @GetMapping("/city/delete")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public String deleteCity(@RequestParam Long id, Model model) {
        loggerService.LogControlller(controllerName, "/city/delete", new ArrayList<String>(Arrays.asList(id.toString())));
        cityService.Delete(id);
        return "redirect:/city/list";
    }

    @GetMapping("/city/edit/{id}")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public String editCity(@PathVariable Long id, Model model) {
        loggerService.LogControlller(controllerName, "/city/edit/{id}", new ArrayList<String>(Arrays.asList(id.toString())));
        model.addAttribute("city", cityService.ReadById(id));
        model.addAttribute("error", 0);
        return "city/edit";
    }

    @PostMapping("/city/edit/{id}")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public String updateCity(@PathVariable Long id, @ModelAttribute City city, Model model) {
        loggerService.LogControlller(controllerName, "/city/edit/{id}", new ArrayList<String>(Arrays.asList(id.toString(), city.toString())));
        city.setId(id);
        if(cityService.Update(city)) {
            return "redirect:/city/list";
        }
        model.addAttribute("error", 1);
        return "city/edit";
    }
}
