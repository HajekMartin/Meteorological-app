package com.example.projekt.Controller;

import com.example.projekt.Entity.City;
import com.example.projekt.Entity.Measurement;
import com.example.projekt.Service.CityService;
import com.example.projekt.Service.MeasurementService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Locale;

@Controller
public class CsvController {

    @Autowired
    private CityService cityService;
    @Autowired
    private MeasurementService measurementService;

    @GetMapping("/csv/page")
    public String Page(Model model) {
        model.addAttribute("cities", cityService.ReadAll());
        return "csv/page";
    }

    @PostMapping("/csv/upload")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("cityId") Long cityId, Model model) {
        if(file.isEmpty()) {
            model.addAttribute("error", 1);
            return this.Page(model);
        }
        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                Measurement tmp = new Measurement();
                tmp.setDate(Long.parseLong(line[0]));
                tmp.setCity(cityService.ReadById(cityId));
                tmp.setTemperature(Double.parseDouble(line[1]));
                tmp.setRain(Double.parseDouble(line[1]));
                tmp.setWindSpeed(Double.parseDouble(line[1]));
                measurementService.Create(tmp);
            }
            return "redirect:/csv/page";
        } catch (IOException | CsvException e) {
            // Neřeším nemám pro to v UI prvky
        } catch (NumberFormatException nfe) {
            // Neřeším nemám pro to v UI prvky
        }
        model.addAttribute("error", 1);
        return this.Page(model);
    }

    @GetMapping("/csv/download")
    public ResponseEntity<Resource> DownloadCSV(@RequestParam("cityId") Long cityId) {
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("date,temperature,rain,windSpeed\n");
        City city = cityService.ReadById(cityId);
        for (Measurement measurement: city.getMeasurementList()) {
            csvContent.append(String.format(Locale.US, "%d,%.2f,%.2f,%.2f\n", measurement.getDate(), measurement.getTemperature(), measurement.getRain(),measurement.getWindSpeed()));
        }
        ByteArrayResource resource = new ByteArrayResource(csvContent.toString().getBytes());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=export.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .contentLength(resource.contentLength())
                .body(resource);
    }
}
