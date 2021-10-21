package com.hashedin.eventhub.gatewayserver.config;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request,
                                                  ErrorAttributeOptions  errorAttributeOptions) {
        Throwable error = this.getError(request);
        log.error("Error occured", error);

        Map<String, Object> map = super.getErrorAttributes(request,
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));
        map.put("errorDescription", "Some error has occurred");
        map.put("title", "Error");
        map.put("tabTitle", "Error");
        map.put("errorDescriptionTitle", "A error has occurred! :(");
        if(error instanceof ExpiredJwtException) {
            map.put("message","Your token has expired.. please log in to continue");
        } else {
            map.put("message","Some error has occurred.. please inform sys-admin");
        }

        return map;
    }
}
