package com.techbloghub.core.authentication.application.jwt;

import com.techbloghub.common.exception.auth.InvalidJwtException;
import org.springframework.stereotype.Component;

import static com.techbloghub.common.exception.common.ErrorType.INVALID_ACCESS_TOKEN;

@Component
public class BearerAuthorizationExtractor {

    private static final String BEARER_TYPE = "Bearer ";

    public String extractAccessToken(String header) {
        if (header != null && header.startsWith(BEARER_TYPE)) {
            return header.substring(BEARER_TYPE.length()).trim();
        }
        throw new InvalidJwtException(INVALID_ACCESS_TOKEN);
    }
}
