package com.app.wiremockTutorial.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class WiremockTutorialController {

    @Value("${resource.url}")
    private String resourceURL;

    @PostMapping("/character")
    public ResponseEntity<String> returnCharacter(@RequestParam("name")String name){
        WebClient webClient = WebClient.create(resourceURL);

         return webClient
                .get()
                .uri("/api/people/?search={name}", name.replace("+","%20"))
                .retrieve()
                .toEntity(String.class)
                .block();

    }
}
