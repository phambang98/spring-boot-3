package com.example.controller;


import com.example.service.WebFluxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class StreamController {
    @Autowired
    private WebFluxService webFluxService;

    @GetMapping(value = "video/{title}", produces = "video/mp4")
    public Mono<Resource> getVideo(@PathVariable String title, @RequestHeader(value = "Range", required = false) String range) {

        return webFluxService.getVideo(title);
    }


    @GetMapping(value = "call-video", produces = "video/mp4")
    public Resource callVideo() {
        return webFluxService.callVideo();
    }
}
