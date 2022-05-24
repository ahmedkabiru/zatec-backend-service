package com.zatec.zatecbackendservice.service;

import com.zatec.zatecbackendservice.model.JokesDto;
import com.zatec.zatecbackendservice.model.JokesSearchResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChuckService {

    Flux<String> getAllChuckCategories();

    Mono<JokesDto> getChuckByCategory(String category);


    Mono<JokesSearchResponseDto> searchChuckJokes(String value);
}
