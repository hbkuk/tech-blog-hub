package com.techbloghub.common.exception.auth;

import com.techbloghub.common.exception.common.ErrorType;
import lombok.Getter;

@Getter
public class ExpiredPeriodJwtException extends UnauthorizedException {

    public ExpiredPeriodJwtException(ErrorType errorType) {
        super(errorType);
    }
}
