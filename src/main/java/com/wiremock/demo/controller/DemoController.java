package com.wiremock.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {

    @Value("${resource.url}")
    private String resourceURL;

    @PostMapping("/character")
    public Mono<String> returnGreeting(@RequestParam("name")String name){
        WebClient webClient = WebClient.create(resourceURL);

         return webClient
                .get()
                .uri("/api/people/?search={name}",name)
                .retrieve()
                .bodyToMono(String.class);

    }
}