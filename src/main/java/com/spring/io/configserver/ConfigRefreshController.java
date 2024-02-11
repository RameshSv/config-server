package com.spring.io.configserver;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigRefreshController {

    @PostMapping("/refresh")
    public void refreshClients(@RequestBody JsonNode payload){
        System.out.println("Hey"+payload);
        String branchName = payload.get("ref").asText();
        String response = "Not required branch :"+branchName;
        if(branchName.equalsIgnoreCase("refs/heads/master")) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(null, headers);

            RestTemplateBuilder builder = new RestTemplateBuilder();
            response = builder.build()
                    .postForObject("http://localhost:9999/actuator/refresh", entity, String.class);
        }
        System.out.println(response);
    }
}
