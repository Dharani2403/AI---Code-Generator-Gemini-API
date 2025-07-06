package com.dharani.aicodegenerator;

import com.google.gson.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GeminiClient {

    private static final String API_KEY = "YOUR_API_KEY";
    private static final String MODEL = "gemini-1.5-flash"; // Or gemini-2.5-pro
    private static final String ENDPOINT =
            "https://generativelanguage.googleapis.com/v1/models/" + MODEL + ":generateContent?key=" + API_KEY;

    public String getExplanation(String topic) {
        try {
            HttpURLConnection connection = getHttpURLConnection(topic);

            int status = connection.getResponseCode();

            if (status != 200) {
                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    StringBuilder errorMsg = new StringBuilder();
                    String line;
                    while ((line = errorReader.readLine()) != null) {
                        errorMsg.append(line);
                    }
                    return "❌ Gemini API returned error code: " + status + "\n" + errorMsg;
                }
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                JsonObject responseJson = JsonParser.parseReader(reader).getAsJsonObject();
                JsonArray candidates = responseJson.getAsJsonArray("candidates");

                if (candidates != null && !candidates.isEmpty()) {
                    JsonObject contentJson = candidates.get(0).getAsJsonObject()
                            .getAsJsonObject("content");
                    JsonArray candidateParts = contentJson.getAsJsonArray("parts");
                    return candidateParts.get(0).getAsJsonObject().get("text").getAsString();
                } else {
                    return "⚠️ No response from Gemini.";
                }
            }

        } catch (Exception e) {
            return "⚠️ Error: Could not connect to Gemini API.";
        }
    }

    private static HttpURLConnection getHttpURLConnection(String topic) throws IOException {
        URL url = new URL(ENDPOINT);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setDoOutput(true);

        String prompt = "Give the code for this '" + topic + "' in simple terms suitable for a beginner. and well-structured, understandable and clean in Java";

        String jsonBody = """
        {
          "contents": [
            {
              "parts": [
                {
                  "text": "%s"
                }
              ]
            }
          ]
        }
        """.formatted(prompt);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        return connection;
    }
}
