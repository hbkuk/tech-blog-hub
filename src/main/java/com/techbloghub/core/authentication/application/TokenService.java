package com.techbloghub.core.authentication.application;

import com.techbloghub.core.authentication.application.dto.KakaoProfileResponse;
import com.techbloghub.core.authentication.application.jwt.JwtTokenProvider;
import com.techbloghub.core.authentication.application.kakao.KakaoClient;
import com.techbloghub.core.authentication.domain.MemberTokens;
import com.techbloghub.core.authentication.domain.RefreshToken;
import com.techbloghub.core.authentication.domain.RefreshTokenRepository;
import com.techbloghub.core.authentication.presentation.dto.KakaoCodeRequest;
import com.techbloghub.core.member.application.MemberService;
import com.techbloghub.core.member.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoClient kakaoClient;

    public MemberTokens generateToken(KakaoCodeRequest request) {
        KakaoProfileResponse kakaoProfileResponse = kakaoClient.requestKakaoProfile(request.getCode());
        Member member = memberService.findOrCreateMemberByKakaoId(kakaoProfileResponse.getId());

        MemberTokens memberTokens = jwtTokenProvider.generateLoginToken(member.getId().toString());
        RefreshToken savedRefreshToken = new RefreshToken(memberTokens.getRefreshToken(),
                member.getId());
        refreshTokenRepository.save(savedRefreshToken);
        return memberTokens;
    }
}
