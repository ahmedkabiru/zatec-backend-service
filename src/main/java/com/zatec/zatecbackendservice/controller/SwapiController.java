package com.zatec.zatecbackendservice.controller;

import com.zatec.zatecbackendservice.api.SwapiApi;
import com.zatec.zatecbackendservice.model.PeopleResponseDto;
import com.zatec.zatecbackendservice.service.SwapiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class SwapiController implements SwapiApi {


    final SwapiService swapiService;

    public SwapiController(SwapiService swapiService) {
        this.swapiService = swapiService;
    }

    @Override
    public Mono<ResponseEntity<PeopleResponseDto>> getAllStarWarsPeople(ServerWebExchange exchange) {
        return swapiService.getAllStarWarsPeople().map(ResponseEntity::ok);
    }
}
