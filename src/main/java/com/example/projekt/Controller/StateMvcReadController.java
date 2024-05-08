package com.example.projekt.Controller;

import com.example.projekt.Service.LoggerService;
import com.example.projekt.Service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class StateMvcReadController {

    @Autowired
    private StateService stateService;
    private final LoggerService loggerService = new LoggerService();
    private static final String controllerName = "StateMvcReadController";

    @GetMapping("/state/list")
    public String getStates(Model model) {
        loggerService.LogControlller(controllerName, "/state/list", new ArrayList<String>(Arrays.asList()));
        model.addAttribute("states", stateService.ReadAll());
        return "state/list";
    }
}
