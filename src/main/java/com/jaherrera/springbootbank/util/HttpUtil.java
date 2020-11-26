package com.jaherrera.springbootbank.util;

import org.springframework.http.HttpHeaders;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class HttpUtil {

    public static HttpHeaders getHeaders(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(APPLICATION_JSON));
        headers.setContentType(APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return headers;
    }
}
