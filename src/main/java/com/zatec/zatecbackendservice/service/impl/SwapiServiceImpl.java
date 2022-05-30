package com.zatec.zatecbackendservice.service.impl;

import com.zatec.zatecbackendservice.model.PeopleResponseDto;
import com.zatec.zatecbackendservice.service.SwapiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SwapiServiceImpl implements SwapiService {

    private final WebClient webClient;


    public SwapiServiceImpl(WebClient.Builder webClient,  @Value("${webClient.baseUrl.swapi}") String baseUrl) {
        this.webClient = webClient
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }


    @Override
    public Mono<PeopleResponseDto> getAllStarWarsPeople() {
        return
                webClient.get().uri("/api/people").retrieve().bodyToMono(PeopleResponseDto.class);
    }

    @Override
    public Mono<PeopleResponseDto> searchStarWarsPeople(String value) {
        return webClient.get()
                .uri(builder -> builder.path("/api/people").queryParam("search", value).build())
                .retrieve()
                .bodyToMono(PeopleResponseDto.class);
    }
}
