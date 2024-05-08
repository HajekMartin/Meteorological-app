package com.example.projekt.Controller;

import com.example.projekt.Entity.State;
import com.example.projekt.Service.LoggerService;
import com.example.projekt.Service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
@ConditionalOnProperty(name = "readmode", havingValue = "false")
public class StateMvcController {

    @Autowired
    private StateService stateService;
    private final LoggerService loggerService = new LoggerService();
    private static final String controllerName = "StateMvcController";

    @GetMapping("/state/add")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public String addState(Model model) {
        loggerService.LogControlller(controllerName, "/state/add", new ArrayList<String>(Arrays.asList()));
        model.addAttribute("state", new State());
        model.addAttribute("error", 0);
        return "state/add";
    }

    @PostMapping("/state/add")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public String insertState(@ModelAttribute State state, Model model) {
        loggerService.LogControlller(controllerName, "/state/add", new ArrayList<String>(Arrays.asList(state.toString())));
        if (stateService.Create(state)) {
            return "redirect:/state/list";
        }
        model.addAttribute("error", 1);
        return "state/add";
    }

    @GetMapping("/state/delete")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public String deleteState(@RequestParam String id, Model model) {
        loggerService.LogControlller(controllerName, "/state/delete", new ArrayList<String>(Arrays.asList(id.toString())));
        stateService.Delete(id); // nemám pro to v UI prvek
        return "redirect:/state/list";
    }

    @GetMapping("/state/edit")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public String editState(@RequestParam String id, Model model) {
        loggerService.LogControlller(controllerName, "/state/edit", new ArrayList<String>(Arrays.asList(id.toString())));
        model.addAttribute("state", stateService.ReadById(id));
        model.addAttribute("error", 0);
        return "state/edit";
    }

    @PostMapping("/state/edit")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public String updateState(@RequestParam String id, @ModelAttribute State state, Model model) {
        loggerService.LogControlller(controllerName, "/state/edit", new ArrayList<String>(Arrays.asList(id.toString(), state.toString())));
        state.setId(id);
        if(stateService.Update(state)) {
            return "redirect:/state/list";
        }
        model.addAttribute("error", 1);
        return "city/edit";
    }
}
