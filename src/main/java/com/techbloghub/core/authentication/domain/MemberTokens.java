package com.techbloghub.core.authentication.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberTokens {

    private final String refreshToken;
    private final String accessToken;
}
