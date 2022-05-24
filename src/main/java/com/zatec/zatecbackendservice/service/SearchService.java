package com.zatec.zatecbackendservice.service;

import com.zatec.zatecbackendservice.model.SearchResultResponseDto;
import reactor.core.publisher.Mono;

public interface SearchService {

    Mono<SearchResultResponseDto>  searchJokesAndStarWars(String query);
}
