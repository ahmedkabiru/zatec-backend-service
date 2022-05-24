package com.zatec.zatecbackendservice.service.impl;

import com.zatec.zatecbackendservice.enums.APIType;
import com.zatec.zatecbackendservice.exception.BadRequestException;
import com.zatec.zatecbackendservice.model.JokesSearchResponseDto;
import com.zatec.zatecbackendservice.model.PeopleResponseDto;
import com.zatec.zatecbackendservice.model.SearchResultResponseDto;
import com.zatec.zatecbackendservice.model.SearchResultResponseMetaDataDto;
import com.zatec.zatecbackendservice.service.ChuckService;
import com.zatec.zatecbackendservice.service.SearchService;
import com.zatec.zatecbackendservice.service.SwapiService;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SearchServiceImpl implements SearchService {

    final ChuckService chuckService;
    final SwapiService swapiService;

    public SearchServiceImpl(ChuckService chuckService, SwapiService swapiService) {
        this.chuckService = chuckService;
        this.swapiService = swapiService;
    }

    @Override
    public Mono<SearchResultResponseDto> searchJokesAndStarWars(String query) {
        if(StringUtils.isNotEmpty(query) && StringUtils.length(query) < 3){
            throw new BadRequestException("search.query: size must be between 3 and 120");
        }
        Mono<JokesSearchResponseDto> jokesSearchResponse = chuckService.searchChuckJokes(query);
        Mono<PeopleResponseDto> peopleResponse = swapiService.searchStarWarsPeople(query);
        return Mono.zip(jokesSearchResponse,peopleResponse).map(
                objects -> {
                    SearchResultResponseDto searchResultResponseDto = new SearchResultResponseDto();
                    if(!objects.getT1().getResult().isEmpty()){
                        searchResultResponseDto.metaData(new SearchResultResponseMetaDataDto()
                                .apiType(APIType.CHUCK_API.name())
                                .apiUrl(APIType.CHUCK_API.getUrl())
                        );
                        searchResultResponseDto.data(objects.getT1());
                    }
                    if(!objects.getT2().getResults().isEmpty()){
                        searchResultResponseDto.metaData(new SearchResultResponseMetaDataDto()
                                .apiType(APIType.SWAPI_API.name())
                                .apiUrl(APIType.SWAPI_API.getUrl())
                        );
                        searchResultResponseDto.data(objects.getT2());
                    }
                    return searchResultResponseDto;
                }
        );
    }
}
