package com.techbloghub.core.auth.fixture;

import com.techbloghub.common.exception.badRequest.BadRequestException;
import com.techbloghub.common.exception.common.ErrorType;

import java.util.Arrays;
import java.util.List;

public enum KakaoMemberFixture {

    라이언("Authorization Code for Lion", 100L, "Access Token for Lion"),
    무지("Authorization Code for Muzi", 101L, "Access Token for Muzi"),
    네오("Authorization Code for Neo", 102L, "Access Token for Neo"),
    어피치("Authorization Code for Apeach", 103L, "Access Token for Apeach");

    public final String 인가_코드;
    public final Long 카카오_회원_번호;
    public final String 토큰;

    KakaoMemberFixture(String 인가_코드, Long 카카오_회원_번호, String 토큰) {
        this.인가_코드 = 인가_코드;
        this.카카오_회원_번호 = 카카오_회원_번호;
        this.토큰 = 토큰;
    }

    public static List<KakaoMemberFixture> 모든_카카오_회원_가져오기() {
        return List.of(values());
    }

    public static String findTokenByCode(String 인가_코드) {
        return Arrays.stream(values())
                .filter(memberFixture -> memberFixture.인가_코드.equals(인가_코드))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(ErrorType.NOT_FOUND_DATA))
                .get토큰();
    }

    public static Long findIdByToken(String 토큰) {
        return Arrays.stream(values())
                .filter(memberFixture -> memberFixture.토큰.equals(토큰))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(ErrorType.NOT_FOUND_DATA))
                .get카카오_회원_번호();
    }

    public String get인가_코드() {
        return 인가_코드;
    }

    public Long get카카오_회원_번호() {
        return 카카오_회원_번호;
    }

    public String get토큰() {
        return 토큰;
    }
}
