package com.zatec.zatecbackendservice.controller;

import com.zatec.zatecbackendservice.api.SearchApi;
import com.zatec.zatecbackendservice.model.SearchResultResponseDto;
import com.zatec.zatecbackendservice.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class SearchController implements SearchApi {

    final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public Mono<ResponseEntity<SearchResultResponseDto>> getSearchResult(String query, ServerWebExchange exchange) {
        return searchService.searchJokesAndStarWars(query).map(ResponseEntity::ok);
    }
}
