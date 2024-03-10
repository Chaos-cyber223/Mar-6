package com.example.demo.Service;

import com.example.demo.Pojo.UniversityPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UniversityService implements UniversityServiceInterface{
    private final RestTemplate restTemplate;
    @Value("${BASE_URL}")
    private String BASE_URL;

    @Autowired
    public UniversityService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public UniversityPojo[] getUniversities() {
        return restTemplate.getForObject(BASE_URL, UniversityPojo[].class);
    }

    @Override
    public UniversityPojo[] getUniversitiesByCountries(String[] countries) {
        List<UniversityPojo> allUniversities = new ArrayList<>();
        for (String country : countries) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                    .queryParam("country", country);
            String url = builder.toUriString().replace("%20", "+");;
//            System.out.println("url:" + url);
            UniversityPojo[] response = restTemplate.getForObject(url, UniversityPojo[].class);
            if (response != null) {
                Collections.addAll(allUniversities, response);
            }
        }
        return allUniversities.toArray(new UniversityPojo[0]);
    }

    @Override
    public CompletableFuture<UniversityPojo[]> getUniversitiesByCountriesAsync(String[] countries) {
        CompletableFuture<UniversityPojo[]>[] futures = Arrays.stream(countries)

                .map(country -> CompletableFuture.supplyAsync(() -> {
                    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                            .queryParam("country", country);
                    String url = builder.toUriString().replace("%20", "+");;
                    System.out.println("completable url:" + url);
                    return restTemplate.getForObject(url, UniversityPojo[].class);
                }))
                .toArray(CompletableFuture[]::new);

        return CompletableFuture.allOf(futures)
                .thenApply(voided -> Arrays.stream(futures)
                        .flatMap(future -> Arrays.stream(future.join()))
                        .toArray(UniversityPojo[]::new));
    }

}
