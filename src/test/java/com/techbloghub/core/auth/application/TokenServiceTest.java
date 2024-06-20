package com.techbloghub.core.auth.application;

import com.techbloghub.common.util.ApplicationTest;
import com.techbloghub.core.authentication.application.TokenService;
import com.techbloghub.core.authentication.application.jwt.JwtTokenProvider;
import com.techbloghub.core.authentication.application.kakao.KakaoClient;
import com.techbloghub.core.authentication.domain.MemberTokens;
import com.techbloghub.core.authentication.domain.RefreshTokenRepository;
import com.techbloghub.core.authentication.presentation.dto.KakaoCodeRequest;
import com.techbloghub.core.member.application.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.techbloghub.core.auth.fixture.KakaoMemberFixture.라이언;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Toke 서비스 테스트")
public class TokenServiceTest extends ApplicationTest {

    @Autowired
    TokenService tokenService;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    KakaoClient kakaoClient;

    @Nested
    class 카카오를_통해_토큰_발급 {

        @Test
        void 토큰_발급_성공() {
            // given
            KakaoCodeRequest 카카오_인가_코드_정보 = new KakaoCodeRequest(라이언.인가_코드);

            // when
            MemberTokens 발급된_회원_토큰_정보 = tokenService.generateToken(카카오_인가_코드_정보);

            // then
            assertThat(발급된_회원_토큰_정보.getAccessToken()).isNotBlank();
            assertThat(발급된_회원_토큰_정보.getRefreshToken()).isNotBlank();
        }
    }
}
