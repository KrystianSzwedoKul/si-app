package com.si.servis;

import com.si.config.WebClientConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebService {

    private final WebClient config;

    public WebService(WebClient config) {
        this.config = config;
    }

    public void isValidSentence(String language, String sentence) {

        String result=   config.get()
                .uri("/api/v1/gemini/"+language+"/"+sentence)
                .retrieve()
                .bodyToFlux(String.class)
                .blockFirst();
        System.out.println(result);
    }
}
