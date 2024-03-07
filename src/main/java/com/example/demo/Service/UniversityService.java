package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
                String modifiedResponse = response.substring(1, response.length() - 1);
                if (!modifiedResponse.isEmpty()) {
                    if (countriesInfo.length() > 1) {
                        countriesInfo.append(",");
                    }
                    countriesInfo.append(modifiedResponse);
                }
            }
        }
        countriesInfo.append("]");
        return countriesInfo.toString();
    }

    public String getUniversitiesByCountriesAsync(String[] countries) {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (String country : countries) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                        .queryParam("country", country);
                String url = builder.toUriString().replace("%20", "+");
                System.out.println("completable url:" + url);
                return restTemplate.getForObject(url, String.class);
            });
            futures.add(future);
        }

        List<String> responses = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        StringBuilder countriesInfo = new StringBuilder("[");
        for (String response : responses) {
            if (response != null) {
                String modifiedResponse = response.substring(1, response.length() - 1);
                if (!modifiedResponse.isEmpty()) {
                    if (countriesInfo.length() > 1) {
                        countriesInfo.append(",");
                    }
                    countriesInfo.append(modifiedResponse);
                }
            }
        }
        countriesInfo.append("]");
        System.out.println("I am using completable");
        return countriesInfo.toString();
    }

}
