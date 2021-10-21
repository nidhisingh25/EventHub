package com.hashedin.eventhub.userservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.jwt.token")
public class JwtTokenProperties {
    private static final long serialVersionUID = 1L;

    private Long timeout;

    private String signingKey;

    /**
     * @return the timeout
     */
    public Long getTimeout() {
        if(timeout==null || timeout<=0) {
            return 0L;
        }
        return timeout;
    }

    /**
     * @param jwtTokenTimeout the timeout to set
     */
    public void setTimeout(Long jwtTokenTimeout) {
        this.timeout = jwtTokenTimeout;
    }

    /**
     * @return the signingKey
     */
    public String getSigningKey() {
        return signingKey;
    }

    /**
     * @param signingKey the signingKey to set
     */
    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }
}
