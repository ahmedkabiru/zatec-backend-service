package com.zatec.zatecbackendservice.service.impl;

import com.zatec.zatecbackendservice.model.JokesDto;
import com.zatec.zatecbackendservice.model.JokesSearchResponseDto;
import com.zatec.zatecbackendservice.service.ChuckService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Component
public class ChuckServiceImpl implements ChuckService {

    private final WebClient webClient;


    public ChuckServiceImpl(WebClient.Builder webClient, @Value("${webClient.baseUrl.chuck}") String baseUrl) {
        this.webClient = webClient
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public Flux<String> getAllChuckCategories() {
        return webClient.get().uri("/jokes/categories").retrieve().bodyToFlux(String.class);
    }

    @Override
    public Mono<JokesDto> getChuckByCategory(String category) {
        return webClient.get()
                .uri(builder -> builder.path("/jokes/random").queryParam("category", category).build())
                .retrieve()
                .bodyToMono(JokesDto.class);
    }

    @Override
    public Mono<JokesSearchResponseDto> searchChuckJokes(String value) {
        return webClient.get()
                .uri(builder -> builder.path("/jokes/search").queryParam("query", value).build())
                .retrieve()
                .bodyToMono(JokesSearchResponseDto.class);
    }
}
