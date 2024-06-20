package com.techbloghub.core.auth.application;

import com.techbloghub.common.util.ApplicationMockTest;
import com.techbloghub.core.authentication.application.TokenService;
import com.techbloghub.core.authentication.application.dto.KakaoProfileResponse;
import com.techbloghub.core.authentication.application.jwt.JwtTokenProvider;
import com.techbloghub.core.authentication.application.kakao.KakaoClient;
import com.techbloghub.core.authentication.domain.MemberTokens;
import com.techbloghub.core.authentication.domain.RefreshToken;
import com.techbloghub.core.authentication.domain.RefreshTokenRepository;
import com.techbloghub.core.authentication.presentation.dto.KakaoCodeRequest;
import com.techbloghub.core.member.application.MemberService;
import com.techbloghub.core.member.domain.Member;
import com.techbloghub.core.member.domain.MemberStatus;
import com.techbloghub.core.member.domain.MemberType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import static com.techbloghub.core.auth.fixture.KakaoMemberFixture.어피치;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Toke 서비스 Mock 테스트")
public class TokenServiceMockTest extends ApplicationMockTest {

    @InjectMocks
    TokenService tokenService;

    @Mock
    KakaoClient kakaoClient;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @Mock
    MemberService memberService;

    @Mock
    RefreshTokenRepository refreshTokenRepository;

    @Nested
    class 카카오를_통해_토큰_발급 {

        @Test
        void 토큰_발급_성공() {
            // given
            when(kakaoClient.requestKakaoProfile(어피치.인가_코드)).thenReturn(new KakaoProfileResponse(어피치.카카오_회원_번호));

            Member 어피치_회원_정보 = new Member(어피치.카카오_회원_번호.toString(), MemberType.KAKAO, MemberStatus.ACTIVE);
            ReflectionTestUtils.setField(어피치_회원_정보, "id", 1L);
            when(memberService.findOrCreateMemberByKakaoId(어피치.카카오_회원_번호))
                    .thenReturn(어피치_회원_정보);

            when(jwtTokenProvider.generateLoginToken(어피치_회원_정보.getId().toString())).thenReturn(new MemberTokens("Access Token", "Refresh Token"));

            // when
            KakaoCodeRequest 카카오_인가_코드_요청_정보 = new KakaoCodeRequest(어피치.인가_코드);
            tokenService.generateToken(카카오_인가_코드_요청_정보);

            // then
            verify(kakaoClient, times(1)).requestKakaoProfile(카카오_인가_코드_요청_정보.getCode());
            verify(memberService, times(1)).findOrCreateMemberByKakaoId(어피치.카카오_회원_번호);
            verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
            verify(jwtTokenProvider, times(1)).generateLoginToken(any());
        }
    }
}
