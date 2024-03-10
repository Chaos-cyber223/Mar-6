package com.example.demo.Service;

import com.example.demo.Pojo.UniversityPojo;

import java.util.concurrent.CompletableFuture;

public interface UniversityServiceInterface {
    UniversityPojo[] getUniversities();
    UniversityPojo[] getUniversitiesByCountries(String[] countries);
    CompletableFuture<UniversityPojo[]> getUniversitiesByCountriesAsync(String[] countries);
}
