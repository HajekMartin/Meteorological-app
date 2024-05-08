package com.example.projekt.Service;

import com.example.projekt.Controller.CityApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class LoggerService {

    private static final Logger logger = LoggerFactory.getLogger(CityApiController.class);

    public void LogControlller(String controllerName, String mappingPath, ArrayList<String> args) {
        String res = "Controller: " + controllerName + "|" + mappingPath;
        for (String arg: args) {
            res += "|" + arg;
        }
        logger.info(res);
    }

    public void LogService(String serviceName, String method, ArrayList<String> args) {
        String res = "Service: " + serviceName + "|" + method;
        for (String arg: args) {
            res += "|" + arg;
        }
        logger.info(res);
    }

}
