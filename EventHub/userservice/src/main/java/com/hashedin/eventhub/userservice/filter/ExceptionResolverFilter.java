package com.hashedin.eventhub.userservice.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * This custom filter helps {@link com.hashedin.eventhub.userservice.exception.handler.GlobalExceptionHandler} to handle some exceptions
 * like {@link io.jsonwebtoken.ExpiredJwtException} which would otherwise be lost in filter-chain.
 * 
 * This filter need to be hooked into the chain very earlier than other filters which
 * might throws exceptions because all preceding filters' exceptions won't be caught.
 *
 */
@Component
public class ExceptionResolverFilter extends OncePerRequestFilter {

	private static final Logger LOG = LoggerFactory.getLogger(ExceptionResolverFilter.class);

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
        	LOG.error("Spring Security Filter Chain Exception:", e);
            resolver.resolveException(request, response, null, e);
        }
    }
}