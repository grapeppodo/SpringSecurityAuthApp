package com.business.authentication;

import com.business.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

/**
 * 외부 인증 서버와 통신하기 위한 클라이언트 역할의 프록시 클래스
 * 역할 : RestTemplate을 이용해 인증 서버에 HTTP 요청 보냄.
 * 애플리케이션의 직접 인증 로직을 구현하는 대신, 인증 서버의 API를 호출해 인증 작업 위임
 *
 */
public class AuthenticationServerProxy {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public AuthenticationServerProxy(RestTemplate restTemplate, @Value("${auth.server.base.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public void sendAuth(String username, String password) {
        String url = baseUrl + "/user/auth";

        var body = new User();
        body.setUsername(username);
        body.setPassword(password);

        var request = new HttpEntity<>(body);

        restTemplate.postForEntity(url, request, Void.class);
    }

    public boolean sendOTP(String username, String code) {
        String url = baseUrl + "/otp/check";

        var body = new User();
        body.setUsername(username);
        body.setCode(code);

        var request = new HttpEntity<>(body);

        var response = restTemplate.postForEntity(url, request, Void.class);

        return response
                .getStatusCode()
                .equals(HttpStatus.OK);
    }

}
