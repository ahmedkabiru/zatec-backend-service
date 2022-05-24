package com.zatec.zatecbackendservice.enums;

public enum APIType {

    CHUCK_API("https://api.chucknorris.io/jokes/"),

    SWAPI_API("https://swapi.dev/api/people/");

    final String url;

    APIType(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
