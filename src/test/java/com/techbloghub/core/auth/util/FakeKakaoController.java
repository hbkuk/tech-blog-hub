package com.techbloghub.core.auth.util;


import com.techbloghub.core.auth.fixture.KakaoMemberFixture;
import com.techbloghub.core.authentication.application.dto.KakaoAccessTokenRequest;
import com.techbloghub.core.authentication.application.dto.KakaoAccessTokenResponse;
import com.techbloghub.core.authentication.application.dto.KakaoProfileResponse;
import com.techbloghub.core.authentication.application.kakao.KakaoAuthProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Profile("test")
@RestController
public class FakeKakaoController {

    private final KakaoAuthProperties kakaoAuthProperties;

    public FakeKakaoController(KakaoAuthProperties kakaoAuthProperties) {
        this.kakaoAuthProperties = kakaoAuthProperties;
    }

    @PostMapping(value = "/oauth/token")
    public ResponseEntity<KakaoAccessTokenResponse> createToken(@RequestBody KakaoAccessTokenRequest request) {
        if (validateRequest(request)) {
            return ResponseEntity.badRequest().build();
        }

        KakaoAccessTokenResponse response = new KakaoAccessTokenResponse(
                "bearer",
                KakaoMemberFixture.findTokenByCode(request.getCode())
        );
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/v2/user/me")
    public ResponseEntity<KakaoProfileResponse> findProfile(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String accessToken,
            @RequestHeader(value = HttpHeaders.CONTENT_TYPE) String contentType) {
        if (validateHeader(accessToken, contentType)) {
            return ResponseEntity.badRequest().build();
        }

        KakaoProfileResponse profileResponse = new KakaoProfileResponse(
                KakaoMemberFixture.findIdByToken(accessToken)
        );
        return ResponseEntity.ok(profileResponse);
    }

    private boolean validateHeader(String authorization, String contentType) {
        if (!authorization.startsWith("Bearer ")) {
            return false;
        }
        return !contentType.equals("application/x-www-form-urlencoded;charset=utf-8");
    }

    private boolean validateRequest(KakaoAccessTokenRequest request) {
        if (!kakaoAuthProperties.getRedirectUri().equals(request.getRedirectUri())) {
            return false;
        }
        return !kakaoAuthProperties.getClientId().equals(request.getClientId());
    }
}
