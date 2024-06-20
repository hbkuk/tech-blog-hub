package com.techbloghub.core.auth.acceptance;

import com.techbloghub.common.util.AcceptanceTest;
import com.techbloghub.core.authentication.presentation.dto.KakaoCodeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.techbloghub.core.auth.fixture.KakaoMemberFixture.라이언;
import static com.techbloghub.core.auth.step.AuthSteps.*;

@DisplayName("인증 인수 테스트")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Nested
    class 카카오_로그인 {

        @Nested
        class 성공 {

            /**
             * When 클라이언트는 카카오로부터 전달받은 정보로 로그인을 요청한다.
             * Then 회원 가입 처리 후 토큰이 발급된다.
             */
            @Test
            void 가입되지_않은_회원에게_토큰_발급() {
                // given
                var 로그인_요청_정보 = new KakaoCodeRequest(라이언.인가_코드);

                // when
                var 카카오_로그인_요청_응답 = 카카오_로그인_요청(로그인_요청_정보);

                // then
                토큰_확인(카카오_로그인_요청_응답);
            }
        }

        @Nested
        class 실패 {

            /**
             * When  클라이언트는 카카오로부터 발급받은 인가 코드를 포함하지 않고 로그인을 요청한다.
             * Then  회원 가입이 진행되지 않고, 토큰이 발급되지 않는다.
             */
            @Test
            void 인가_코드를_전달하지_않은_경우() {
                // when
                실패하는_로그인_요청();
            }
        }
    }

    @Nested
    class 로그아웃 {

        @Nested
        class 성공 {
            // TODO: Access Token 인 메모리 DB 추가, 블랙리스트 테이블에 Refresh Token 추가

            @Test
            void 로그아웃_처리() {
                // given

                // when

                // then
            }
        }

        @Nested
        class 실패 {

            // TODO: 두개의 토큰을 주지않은 경우
        }
    }

}
