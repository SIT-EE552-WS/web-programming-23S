package edu.stevens.friccobo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PostMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                        "id":"12346",
                        "joke": "Why did the chicken cross the road?"
                        }
                        """))
                .uri(URI.create("http://localhost:8080/joke/v2"))
                .header("Accept", "text/plain")
                .build();

        HttpRequest request2 = HttpRequest
                .newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                        "id":"12346",
                        "joke": "Why the long face?"
                        }
                        """))
                .uri(URI.create("http://localhost:8080/joke/v2"))
                .header("Accept", "text/plain")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        System.out.println(response2.body());


    }
}
