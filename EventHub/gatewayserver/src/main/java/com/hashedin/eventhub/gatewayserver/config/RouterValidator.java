package com.hashedin.eventhub.gatewayserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    @Autowired
    private AppProperties appProperties;

    public final List<String> openApiEndpoints= List.of(
            "/user/authenticate",
            "/user/signup",
            "/user/username",
            "/event/allevents",
            "/event/categories",
            "/event/popularevents",
            "/event/upcomingevents",
            "/event/eventid",
            "/event/category",
            "/event/download/user/"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
