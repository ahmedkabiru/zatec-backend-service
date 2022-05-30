package com.zatec.zatecbackendservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zatec.zatecbackendservice.model.PeopleDto;
import com.zatec.zatecbackendservice.model.PeopleResponseDto;
import com.zatec.zatecbackendservice.service.impl.ChuckServiceImpl;
import com.zatec.zatecbackendservice.service.impl.SwapiServiceImpl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;

class SwapiServiceTest {

    SwapiService swapiService;
    ObjectMapper objectMapper;

    MockWebServer mockWebServer;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        objectMapper = new ObjectMapper();
        swapiService = new SwapiServiceImpl(WebClient.builder(),mockWebServer.url("/").toString());
    }

    @Test
    void getAllStarWarsPeople() throws JsonProcessingException {
        PeopleDto peopleDto = PeopleDto.builder()
                .name("Luke Skywalker")
                .height("172")
                .mass("77")
                .hairColor("blond")
                .skinColor("fair")
                .eyeColor("blue")
                .birthYear("19BBY")
                .gender("male")
                .build();
        PeopleDto peopleDto2 = PeopleDto.builder()
                .name("C-3PO")
                .height("167")
                .mass("75")
                .hairColor("n/a")
                .skinColor("gold")
                .eyeColor("yello")
                .birthYear("112BBY")
                .gender("n/a")
                .build();
        PeopleResponseDto peopleResponseDto =  new PeopleResponseDto();
        peopleResponseDto.setCount("82");
        peopleResponseDto.setNext("https://swapi.dev/api/people/?page=2");
        peopleResponseDto.setPrevious(null);
        peopleResponseDto.setResults(Arrays.asList(peopleDto,peopleDto2));
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .addHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
                .setBody(objectMapper.writeValueAsString(peopleResponseDto))
        );
       Mono<PeopleResponseDto> peopleResponse = swapiService.getAllStarWarsPeople();

        StepVerifier.create(peopleResponse)
                .assertNext(peoples -> {
                    assertThat(peoples.getResults(), instanceOf(List.class));
                    assertThat(peoples.getResults().size(), equalTo(2));
                }).verifyComplete();



    }

    @Test
    void searchStarWarsPeople() {
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }
}