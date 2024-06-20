package com.techbloghub.common.config;

import com.techbloghub.core.authentication.application.kakao.KakaoAuthProperties;
import com.techbloghub.core.authentication.application.kakao.KakaoUserProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {KakaoAuthProperties.class, KakaoUserProperties.class})
public class ConfigurationConfig {
}
