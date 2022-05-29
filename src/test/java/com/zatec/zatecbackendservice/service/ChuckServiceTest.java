package com.zatec.zatecbackendservice.service;

import com.zatec.zatecbackendservice.service.impl.ChuckServiceImpl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


class ChuckServiceTest {

    private MockWebServer mockWebServer;
    private ChuckService chuckService;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        chuckService = new ChuckServiceImpl(WebClient.builder(),mockWebServer.url("/").toString());
    }

    @Test
    void getAllChuckCategories() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse());
        chuckService.getAllChuckCategories().subscribe();
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        //assertThat(recordedRequest.getMethod(), equalTo("GET"));
        ////assertThat(recordedRequest.getPath(), equalTo("/users/1"));
    }

    @Test
    void getChuckByCategory() {
    }

    @Test
    void searchChuckJokes() {
    }


    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

}