package org.churk.memeapi.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

public class RequestFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        StringBuilder logMessage = new StringBuilder();
        logMessage.append("Incoming request: ")
                .append(request.getMethod())
                .append(" ")
                .append(request.getRequestURI())
                .append(" from ")
                .append(request.getRemoteAddr());

        Enumeration<String> parameterNames = request.getParameterNames();
        if (parameterNames.hasMoreElements()) {
            logMessage.append(" with parameters: ");
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String paramValue = request.getParameter(paramName);
                logMessage.append(paramName).append("=").append(paramValue);
                if (parameterNames.hasMoreElements()) {
                    logMessage.append(", ");
                }
            }
        }

        logger.info(logMessage.toString());

        filterChain.doFilter(request, response);
    }
}
