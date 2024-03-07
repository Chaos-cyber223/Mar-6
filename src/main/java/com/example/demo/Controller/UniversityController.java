package com.example.demo.Controller;

import com.example.demo.Service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UniversityController {
    private final UniversityService universityService;

    @Autowired
    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }


    @GetMapping("/universities")
    public ResponseEntity<?> getUniversitiesByCountry(@RequestParam(required = false) String[] country) {
        if (country != null && country.length > 0) {
            return ResponseEntity.ok(universityService.getUniversitiesByCountries(country));
        } else {
            return ResponseEntity.ok(universityService.getUniversities());
        }
    }


}
