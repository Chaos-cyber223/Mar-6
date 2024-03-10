package com.example.demo.Service;

import com.example.demo.Pojo.UniversityPojo;

public interface UniversityServiceInterface {
    UniversityPojo[] getUniversities();
    UniversityPojo[] getUniversitiesByCountries(String[] countries);
    UniversityPojo[] getUniversitiesByCountriesAsync(String[] countries);
}
