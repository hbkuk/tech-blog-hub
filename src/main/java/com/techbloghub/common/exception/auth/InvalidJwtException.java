package com.techbloghub.common.exception.auth;

import com.techbloghub.common.exception.common.ErrorType;
import lombok.Getter;

@Getter
public class InvalidJwtException extends UnauthorizedException {

    public InvalidJwtException(ErrorType errorType) {
        super(errorType);
    }
}
