package com.example.demo.Pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class UniversityPojo {
        @JsonProperty("alpha_two_code")
        private String alphaTwoCode;

        @JsonProperty("web_pages")
        private List<String> webPages;

        @JsonProperty("state-province")
        private String stateProvince;
        @JsonProperty("name")
        private String name;
        @JsonProperty("domains")
        private List<String> domains;
        @JsonProperty("country")
        private String country;
}

