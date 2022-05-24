package com.zatec.zatecbackendservice.controller;

import com.zatec.zatecbackendservice.api.ChuckApi;
import com.zatec.zatecbackendservice.model.JokesDto;
import com.zatec.zatecbackendservice.service.ChuckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ChuckController implements ChuckApi {
    final ChuckService chuckService;

    public ChuckController(ChuckService chuckService) {
        this.chuckService = chuckService;
    }

    @Override
    public Mono<ResponseEntity<Flux<String>>> getAllJokesCategories(ServerWebExchange exchange) {

        return Mono.just(
                ResponseEntity.ok(
                        chuckService.getAllChuckCategories()
                ));
    }

    @Override
    public Mono<ResponseEntity<JokesDto>> getRandomJokesByCategory(String category, ServerWebExchange exchange) {
        return chuckService.getChuckByCategory(category).map(ResponseEntity::ok);
    }

}
