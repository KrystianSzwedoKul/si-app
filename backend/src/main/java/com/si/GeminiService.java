package com.si;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GeminiService {

    private final HttpClient client;
    private final String API_KEY = "AIzaSyA_P72ZiLCBwPbSfo3xfap-bJyx9R-nRcQ";
    private final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + API_KEY;

    public GeminiService(HttpClient client) {
        this.client = client;
    }

    public String getMessage() {

        String jsonPayload =
                """
                        {
                          "contents": [
                            {
                              "parts": [
                                {
                                  "text": "Sprawdź, czy podane zdanie jest poprawne w podanym języku (gramatycznie i znaczeniowo).\\n\\nWejście:\\nJęzyk: %s\\nZdanie: %s\\n\\nZasady:\\n- Jeśli zdanie jest poprawne → \\"czy_poprawny\\": \\"tak\\"\\n- Jeśli zdanie jest niepoprawne → \\"czy_poprawny\\": \\"nie\\"\\n- Zawsze podaj poprawioną wersję zdania w polu \\"poprawne_zdanie\\"\\n- Odpowiedź zwróć WYŁĄCZNIE w formacie JSON\\n\\nFormat odpowiedzi:\\n{\\n  \\"czy_poprawny\\": \\"tak/nie\\",\\n  \\"poprawne_zdanie\\": \\"...\\"\\n}"
                                }
                              ]
                            }
                          ]
                        }
                        """.formatted("angielski","I am angring");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            }
        } catch (Exception e) {
            System.out.println("Błąd podczas komunuikacji z api");
        }

        return "Hello";
    }
}
