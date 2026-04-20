package com.si;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class GeminiParser {


    public GeminiResult parse(String json) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        String text = root.path("candidates")
                .get(0)
                .path("content")
                .get(0)
                .path("parts")
                .get(0)
                .path("text")
                .asText();
        text = text.replace("```json", "")
                .replace("```", "")
                .trim();

        return mapper.readValue(text, GeminiResult.class);
    }
}
