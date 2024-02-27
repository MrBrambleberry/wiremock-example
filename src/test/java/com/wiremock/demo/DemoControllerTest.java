package com.wiremock.demo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.wiremock.demo.controller.DemoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoControllerTest {

    @Autowired
    DemoController demoController;

   WireMockServer wm = new WireMockServer(8080);

    @Test
    void find_character_by_name_2XX(){
        wm.start();
        wm.stubFor(get(urlEqualTo("/api/people/?search=Luke%20Skywalker"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("<response>Some content</response>")));

        ResponseEntity<String> response = demoController.returnCharacter("Luke Skywalker");

        assertTrue(response.getStatusCode().is2xxSuccessful());
        wm.verify(getRequestedFor(urlPathMatching("/api/people/")));
        wm.stop();
    }
}
