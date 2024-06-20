package com.techbloghub.core.authentication.application.kakao;

import com.techbloghub.common.util.WebClientUtil;
import com.techbloghub.core.authentication.application.dto.KakaoAccessTokenRequest;
import com.techbloghub.core.authentication.application.dto.KakaoAccessTokenResponse;
import com.techbloghub.core.authentication.application.dto.KakaoProfileResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@AllArgsConstructor
@Slf4j
public class KakaoClient {

    private final WebClientUtil webClientUtil;
    private final KakaoAuthProperties kakaoAuthProperties;
    private final KakaoUserProperties kakaoUserProperties;

    public KakaoProfileResponse requestKakaoProfile(String code) {
        String accessToken = requestKakaoToken(code);
        return requestKakaoUserInfo(accessToken);
    }

    private String requestKakaoToken(String code) {
        return webClientUtil
                .post(kakaoAuthProperties.getTokenUri(),
                        toFormData(createKakaoAccessTokenRequest(code)),
                        MediaType.APPLICATION_FORM_URLENCODED,
                        KakaoAccessTokenResponse.class)
                .doOnError(ex -> log.error("Error requesting Kakao token: {}", ex.getMessage()))
                .block()
                .getAccessToken();
    }

    private KakaoProfileResponse requestKakaoUserInfo(String accessToken) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        return webClientUtil
                .get(kakaoUserProperties.getProfileUri(),
                        headers,
                        KakaoProfileResponse.class)
                .doOnError(ex -> log.error("Error requesting Kakao User Info: {}", ex.getMessage()))
                .block();
    }

    private KakaoAccessTokenRequest createKakaoAccessTokenRequest(String code) {
        return new KakaoAccessTokenRequest(
                "authorization_code",
                code,
                kakaoAuthProperties.getClientId(),
                kakaoAuthProperties.getRedirectUri()
        );
    }

    private MultiValueMap<String, String> toFormData(KakaoAccessTokenRequest request) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", request.getGrantType());
        formData.add("code", request.getCode());
        formData.add("client_id", request.getClientId());
        formData.add("redirect_uri", request.getRedirectUri());
        return formData;
    }
}
