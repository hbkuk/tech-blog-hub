package com.techbloghub.common.exception.auth;

import com.techbloghub.common.exception.common.ErrorType;
import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {

    private final int code;

    public UnauthorizedException(final ErrorType errorType) {
        super(errorType.getMessage());
        this.code = errorType.getCode();
    }
}
