package com.app.wiremockTutorial.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class WiremockTutorialController {

    @Value("${resource.url}")
    private String resourceURL;

    @GetMapping("/character")
    public Mono<ResponseEntity<String>> returnCharacter(@RequestParam("name")String name){
        WebClient webClient = WebClient.create(resourceURL);
        String encodedName = name.replace("+", "%20");

        return webClient
                .get()
                .uri("/api/people/?search={name}", encodedName)
                .retrieve()
                .toEntity(String.class);
    }
}
