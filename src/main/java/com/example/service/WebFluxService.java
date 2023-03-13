package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@Service
public class WebFluxService {

    private static final String FORMAT = "classpath:videos/%s.mp4";

    @Autowired
    private ResourceLoader resourceLoader;

    public Mono<Resource> getVideo(String title) {
        return Mono.fromSupplier(() -> this.resourceLoader.getResource(String.format(FORMAT, title)));
    }


    private RestTemplate restTemplate = new RestTemplate();

    public Resource callVideo() {
        ResponseEntity responseEntity = restTemplate.getForEntity("https://www.w3schools.com/html/mov_bbb.mp4", Resource.class);
//        Mono<Resource> resourceMono = (Mono<Resource>) responseEntity.getBody();
        return (Resource) responseEntity.getBody();
    }

}