package com.zatec.zatecbackendservice.service;

import com.zatec.zatecbackendservice.model.PeopleResponseDto;
import reactor.core.publisher.Mono;

public interface SwapiService {

    Mono<PeopleResponseDto> getAllStarWarsPeople();

    Mono<PeopleResponseDto>  searchStarWarsPeople(String value);
}
