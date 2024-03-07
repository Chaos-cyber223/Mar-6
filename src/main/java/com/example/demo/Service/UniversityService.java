package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UniversityService {
    private final RestTemplate restTemplate;
    public String BASE_URL = "http://universities.hipolabs.com/search";

    @Autowired
    public UniversityService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public String getUniversities() {
        return restTemplate.getForObject(BASE_URL, String.class);
    }

    public String getUniversitiesByCountries(String[] countries) {
        StringBuilder countriesInfo = new StringBuilder("[");
        for (String country : countries) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                    .queryParam("country", country);
            String url = builder.toUriString().replace("%20", "+");;
            System.out.println("URL being called: " + url);
            String response = restTemplate.getForObject(url, String.class);
//            System.out.println("Response: " + response);
            if (response != null) {
                String trimmedResponse = response.substring(1, response.length() - 1);
                if (!trimmedResponse.isEmpty()) {
                    if (countriesInfo.length() > 1) {
                        countriesInfo.append(",");
                    }
                    countriesInfo.append(trimmedResponse);
                }
            }
        }
        countriesInfo.append("]");
        return countriesInfo.toString();
    }

}
