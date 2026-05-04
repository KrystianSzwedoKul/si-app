package com.si.servis;

import com.nimbusds.jose.shaded.gson.Gson;
import com.si.config.WebClientConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class WebService {

    private final WebClient config;

    public WebService(WebClient config) {
        this.config = config;
    }

    public Map<String,String> isValidSentence(String language, String sentence) {

        String result = config.get()
                .uri("/api/v1/gemini/" + language + "/" + sentence)
                .retrieve()
                .bodyToFlux(String.class)
                .blockFirst();
        Gson gson = new Gson();
        Map<String,String> map = gson.fromJson(result, Map.class);
        return map;
    }
}
