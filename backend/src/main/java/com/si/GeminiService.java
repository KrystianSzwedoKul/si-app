package com.si;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@Service
public class GeminiService {

    private final HttpClient client;
    private final GeminiParser parser;
    private final String API_KEY = "AIzaSyD5KlHZlJqq3S2VvNPeAYO1Cs3trodiE58";
    private final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.1-flash-lite-preview:generateContent?key=" + API_KEY;
    private final String TEXT_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.1-flash-lite-preview:generateContent?key=" + API_KEY;
    private final String IMAGE_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + API_KEY;

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
                .uri(URI.create(TEXT_API_URL))
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

    public Boolean isCorrect(byte[] image, String number) {


        String base64Image = Base64.getEncoder().encodeToString(image);
        String jsonPayload =
                """
                        {
                          "contents": [
                            {
                              "parts": [
                                {
                                  "text": "Sprawdź czy na zdjęciu znajduje się tablica rejestracyjna: %s. Zwróć odpowiedź wyłącznie w formacie JSON: { 'found':true/false, 'detected_plate' : 'numer lub null'} Jeśli nie jesteś pewny - ustaw found=false;"
                                },
                                {
                                    "inline_data" : {
                                        "mime_type" : "image/jpeg",
                                        "data" : "%s"
                                    }
                                }
                              ]
                            }
                          ]
                        }
                        """.formatted(number, base64Image);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IMAGE_API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            if (response.statusCode() == 200) {
                ImageResponse imageResponse = parser.paresImageResponse(response.body());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
