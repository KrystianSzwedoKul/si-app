package com.si;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GeminiService {

    private final HttpClient client;
    private final GeminiParser parser;
    private final String API_KEY = "AIzaSyD5KlHZlJqq3S2VvNPeAYO1Cs3trodiE58";
    private final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.1-flash-lite-preview:generateContent?key=" + API_KEY;

    public GeminiService(HttpClient client, GeminiParser parser) {
        this.client = client;
        this.parser = parser;
    }

    public GeminiResult getMessage(String language, String sentence) {

        String jsonPayload =
                """
                        {
                          "contents": [
                            {
                              "parts": [
                                {
                                  "text": "Sprawdź, czy podane zdanie jest poprawne w podanym języku (gramatycznie i znaczeniowo).\\n\\nWejście:\\nJęzyk: %s\\nZdanie: %s\\n\\nZasady:\\n- Jeśli zdanie jest poprawne → \\"isCorrect\\": \\"Yes\\"\\n- Jeśli zdanie jest niepoprawne → \\"isCorrect\\": \\"No\\"\\n- Zawsze podaj poprawioną wersję zdania w polu \\"poprawne_zdanie\\"\\n- Odpowiedź zwróć WYŁĄCZNIE w formacie JSON\\n\\nFormat odpowiedzi:\\n{\\n  \\"isCorrect\\": \\"Yes/No\\",\\n  \\"correctSentence\\": \\"...\\"\\n}"
                                }
                              ]
                            }
                          ]
                        }
                        """.formatted(language, sentence);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return parser.parse(response.body());
            }
        } catch (Exception e) {
            System.out.println("Błąd podczas komunuikacji z api");
        }

        return new GeminiResult(sentence, language);
    }
}
