package com.example.teamcity.api.requests;

import com.example.teamcity.api.enums.Endpoint;
import io.restassured.specification.RequestSpecification;

public class Request {
    /**
     * Request - это класс, который хранит в себе описание меняющихся от запроса к запросу параметров, таких как:
     * спецификация - информация, кто делает запрос, какой юзер, эндпоинт (relative URL, model), DTO - в которую мы хотим сериализовать даннве в итоге
     */
    protected final RequestSpecification spec;
    protected final Endpoint endpoint;

    public Request(RequestSpecification spec, Endpoint endpoint) {
        this.spec = spec;
        this.endpoint = endpoint;
    }
}
