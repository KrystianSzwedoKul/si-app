package com.si;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gemini/")
public class GeminiController {

    private final GeminiService service;

    public GeminiController(GeminiService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String  getMessage() {

        return service.getMessage();
    }
}
