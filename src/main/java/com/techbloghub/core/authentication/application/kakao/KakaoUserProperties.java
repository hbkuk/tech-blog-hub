package com.techbloghub.core.authentication.application.kakao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("kakao.user")
public class KakaoUserProperties {

    private String profileUri;
}
