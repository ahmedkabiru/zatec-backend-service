package com.zatec.zatecbackendservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zatec.zatecbackendservice.model.JokesDto;
import com.zatec.zatecbackendservice.model.JokesSearchResponseDto;
import com.zatec.zatecbackendservice.service.impl.ChuckServiceImpl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


class ChuckServiceTest {

    private MockWebServer mockWebServer;
    private ChuckService chuckService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        objectMapper = new ObjectMapper();
        chuckService = new ChuckServiceImpl(WebClient.builder(),mockWebServer.url("/").toString());
    }

    @Test
    void getAllChuckCategories() throws JsonProcessingException {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                        .setHeader("Content-Type", "application/json")
                        .setBody(objectMapper.writeValueAsString(Arrays.asList("animal", "career", "celebrity")))
        );
        Flux<String> categories  = chuckService.getAllChuckCategories().log();
        StepVerifier.create(categories).assertNext(result -> assertThat(result, containsString("animal"))).verifyComplete();
    }

    @Test
    void getChuckByCategory() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("""
                        {
                            "categories": [
                                "animal"
                            ],
                            "created_at": "2020-01-05 13:42:19.104863",
                            "icon_url": "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
                            "id": "o0sukejatqchi7oyjms6mw",
                            "updated_at": "2020-01-05 13:42:19.104863",
                            "url": "https://api.chucknorris.io/jokes/o0sukejatqchi7oyjms6mw",
                            "value": "Chuck Norris can set ants on fire with a magnifying glass. At night."
                        }""")
        );
        Mono<JokesDto> joke = chuckService.getChuckByCategory("animal");
        StepVerifier.create(joke).assertNext(result -> assertThat(result.getValue(), containsString("Chuck Norris can set ants on fire with a magnifying glass. At night"))).verifyComplete();
    }

    @Test
    void searchChuckJokes() throws JsonProcessingException {
        JokesDto jokesDto = new JokesDto();
        jokesDto.categories(List.of("animal"));
        jokesDto.createdAt("2020-01-05 13:42:19.104863");
        jokesDto.iconUrl("https://assets.chucknorris.host/img/avatar/chuck-norris.png");
        JokesDto jokesDto2 = new JokesDto();
        jokesDto.categories(List.of("vehicle"));
        jokesDto.createdAt("2020-01-05 13:42:19.104863");
        jokesDto.iconUrl("https://assets.chucknorris.host/img/avatar/chuck-norris.png");
        JokesSearchResponseDto jokesSearchResponseDto = new JokesSearchResponseDto();
        jokesSearchResponseDto.setResult(Arrays.asList(jokesDto,jokesDto2));
        jokesSearchResponseDto.setTotal(1);
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(objectMapper.writeValueAsString(jokesSearchResponseDto)));
                var jokes = chuckService.searchChuckJokes("abc");
                StepVerifier.create(jokes)
                        .assertNext( joke -> {
                            List<JokesDto> jokesDtoList = joke.getResult();
                            assertThat(jokesDtoList , instanceOf(List.class));
                            assertThat(joke.getTotal(), equalTo(1));
                            assertThat(joke.getResult().size(), equalTo(2));
                        }).verifyComplete();
    }


    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

}