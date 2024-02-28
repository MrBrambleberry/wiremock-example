package com.app.wiremockTutorial;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.app.wiremockTutorial.controller.WiremockTutorialController;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WiremockTutorialControllerTests {

    @Autowired
    WiremockTutorialController wiremockTutorialController;

    WireMockServer wm = new WireMockServer();

    @BeforeEach
    void setup(){
        wm.start();
    }

    @AfterEach
    void teardown(){
        wm.stop();
    }

    @Test
    void match_character_by_name() {
        String expectedResponse = "{\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"name\": \"Luke Skywalker\",\n" +
                "            \"height\": \"172\",\n" +
                "            \"mass\": \"77\",\n" +
                "            \"hair_color\": \"blond\",\n" +
                "            \"skin_color\": \"fair\",\n" +
                "            \"eye_color\": \"blue\",\n" +
                "            \"birth_year\": \"19BBY\",\n" +
                "        }\n" +
                "    ]\n" +
                "}";


        stubFor(get(urlEqualTo("/api/people/?search=Luke%20Skywalker"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(expectedResponse)));

        ResponseEntity<String> response = wiremockTutorialController.returnCharacter("Luke Skywalker").block();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(),expectedResponse);
        verify(getRequestedFor(urlPathMatching("/api/people/")));

    }

    @Test
    void empty_response_due_to_no_match(){
        String expectedResponse = "{\n" +
                "    \"results\": []\n" +
                "}";

        stubFor(get(urlEqualTo("/api/people/?search=Jean-Luc%20Picard"))
                .willReturn(aResponse().withStatus(200).withBody(expectedResponse)));

        ResponseEntity<String> response = wiremockTutorialController.returnCharacter("Jean-Luc Picard").block();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(),expectedResponse);
        verify(getRequestedFor(urlPathMatching("/api/people/")));
    }
}
