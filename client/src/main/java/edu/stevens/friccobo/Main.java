package edu.stevens.friccobo;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest
                    .newBuilder()
                    .GET()
                    .uri(URI.create("https://icanhazdadjoke.com/search?term=cat"))
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<DadJokeSearchResults> response = httpClient.send(request, new DadJokeHandler());
            DadJokeSearchResults dadJoke = response.body();
            System.out.println(dadJoke);
            for (DadJoke joke : dadJoke.results()){
                System.out.println(joke.joke());
            }
        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static class DadJokeHandler implements HttpResponse.BodyHandler<DadJokeSearchResults> {
        @Override
        public HttpResponse.BodySubscriber<DadJokeSearchResults> apply(HttpResponse.ResponseInfo responseInfo) {
            return HttpResponse.BodySubscribers.mapping(
                    HttpResponse.BodySubscribers.ofInputStream(),
                    (inputStream -> {
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            return objectMapper.readValue(inputStream, DadJokeSearchResults.class);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
            );
        }
    }

    public record DadJoke(
            String joke,
            String id
    ) {
    }

    public record DadJokeSearchResults(
            int current_page,
            int limit,
            int next_page,
            int previous_page,
            int status,
            int total_jokes,
            int total_pages,
            String search_term,
            List<DadJoke> results
    ) {
    }
}