package com.techbloghub.core.auth.step;

import com.techbloghub.core.authentication.presentation.dto.KakaoCodeRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthSteps {

    public static ExtractableResponse<Response> 카카오_로그인_요청(KakaoCodeRequest request) {
        return given()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/auth/kakao")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    public static void 토큰_확인(ExtractableResponse<Response> 카카오_로그인_요청_응답) {
        String 발급된_액세스_토큰 = 카카오_로그인_요청_응답.jsonPath().getString("accessToken");
        assertThat(발급된_액세스_토큰).isNotBlank();

        String 발급된_리프레시_토큰 = 카카오_로그인_요청_응답.cookie("refresh-token");
        assertThat(발급된_리프레시_토큰).isNotBlank();
    }

    public static void 실패하는_로그인_요청() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/auth/kakao")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();
    }
}
