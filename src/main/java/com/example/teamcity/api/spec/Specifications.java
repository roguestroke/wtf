package com.example.teamcity.api.spec;

import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Specifications {

    private static Specifications spec;

    private static RequestSpecBuilder reqBuilder() {
        var requestBuilder = new RequestSpecBuilder();
        requestBuilder.addFilter(new RequestLoggingFilter());
        requestBuilder.addFilter(new ResponseLoggingFilter());
        return requestBuilder;
    }

    public static RequestSpecification superUserSpec() {
        var requestBuilder = reqBuilder();
        requestBuilder.setBaseUri("http://%s:%s@%s:%s".formatted(
                        "",
                        Config.getProperty("superUserToken"),
                        Config.getProperty("host"),
                        Config.getProperty("port")))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON);
        return requestBuilder.build();
    }

    public static RequestSpecification unauthSpec() {
        var requestBuilder = reqBuilder();
        return requestBuilder.build();
    }

    public static RequestSpecification authSpec(User user) {
        var requestBuilder = reqBuilder();
        requestBuilder.setBaseUri("http://%s:%s@%s:%s".formatted(
                        user.getUsername(),
                        user.getPassword(),
                        Config.getProperty("host"),
                        Config.getProperty("port")))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON);
        return requestBuilder.build();
    }
}
