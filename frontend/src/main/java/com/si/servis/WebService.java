package com.si.servis;

import com.nimbusds.jose.shaded.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebService {

    private final WebClient config;

    public WebService(WebClient config) {
        this.config = config;
    }

    public Map<String, String> isValidSentence(String language, String sentence) {

        String result = config.get()
                .uri("/api/v1/gemini/" + language + "/" + sentence)
                .retrieve()
                .bodyToFlux(String.class)
                .blockFirst();
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(result, Map.class);
        return map;
    }

    public boolean isValidNumber(String number, byte[] image) {

        Map<String, Object> body = new HashMap<>();
        body.put("number", number);
        body.put("image", image);

        Boolean result = config.post()
                .uri("/api/v1/gemini/")
                .bodyValue(body)
                .retrieve()
                .bodyToFlux(Boolean.class)
                .blockFirst();
        return result;
    }
}
