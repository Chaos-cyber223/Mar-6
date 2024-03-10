package com.example.demo.Controller;

import com.example.demo.Pojo.UniversityPojo;
import com.example.demo.Service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

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

    @GetMapping("/universities/async")
    public CompletableFuture<ResponseEntity<?>> getUniversitiesByCountryAsync(@RequestParam(required = false) String[] country) {
        if (country != null && country.length > 0) {
            return universityService.getUniversitiesByCountriesAsync(country)
                    .thenApply(ResponseEntity::ok);
        } else {
            UniversityPojo[] universities = universityService.getUniversities();
            return CompletableFuture.completedFuture(ResponseEntity.ok(universities));
        }
    }
}
