package com.si;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gemini/")
public class GeminiController {

    private final GeminiService service;

    public GeminiController(GeminiService service) {
        this.service = service;
    }

    @GetMapping("/{language}/{sentence}")
    public GeminiResult  getMessage(@PathVariable("language") String language,
                                    @PathVariable("sentence") String sentence) {

        return service.getMessage(language,sentence);
    }

    @PostMapping("/")
    public Boolean isValid(@RequestBody ImageRequest request) {

        return service.isCorrect(request.getImage(), request.getNumber());
    }

}
