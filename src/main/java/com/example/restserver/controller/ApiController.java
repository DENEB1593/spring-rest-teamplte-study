package com.example.restserver.controller;

import com.example.restserver.dto.Req;
import com.example.restserver.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@RestController
@RequestMapping("/api/server")
public class ApiController {
//    https://openapi.naver.com/v1/search/local.json
//    query=%EC%88%9C%EB%8C%80%EA%B5%AD
//    &display=10
//    &start=1
//    &sort=random

    final String NAVER_CLIENT_ID = "2A6fsKu7l1M6MKxGCTxi";
    final String NAVER_CLIENT_KEY = "yGtxoqev9p";

    @GetMapping("/naver")
    public ResponseEntity<String> nameApi() {

        String query = "순대국밥";

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query", query)
                .queryParam("display",10)
                .queryParam("start",1)
                .queryParam("sort","random")
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id",NAVER_CLIENT_ID)
                .header("X-Naver-Client-Secret",NAVER_CLIENT_KEY)
                .build();

        return restTemplate.exchange(req, String.class);
    }

    @GetMapping("/hello")
    public User hello() {
        User user = new User();
        user.setName("steve");
        user.setAge(20);
        return user;
    }

    @PostMapping("/user/{userId}/name/{userName}")
    public Req<User> post(@RequestBody Req<User> user,
                          @PathVariable int userId,
                          @PathVariable String userName,
                          @RequestHeader("x-authorization") String authorization,
                          @RequestHeader("custom-header") String header) {

        log.info("client request : {}", user);
        log.info("client x-authorization : {}", authorization);
        log.info("client custom-header : {}", header);
        log.info("client userId : {}", userId);
        log.info("client userName : {}", userName);

        Req<User> response = new Req<>();
        response.setHeader(new Req.Header());
        response.setBody(user.getBody());

        return response;
    }

}
