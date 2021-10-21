package com.hashedin.eventhub.gatewayserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.permit")
@Getter
@Setter
public class AppProperties {

    private String auth;

    private String signup;
}
