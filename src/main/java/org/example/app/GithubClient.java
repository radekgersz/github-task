package org.example.app;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
@Slf4j
@Getter
@Setter
public class GithubClient {
    @Value("${base.url}")
    private String BASE_URL;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<GithubRepoResponseDTO> getUserRepositories(String username){
        try{
            String API_URL = BASE_URL + "/users/" + username + "/repos";
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Accept", "application/vnd.github.v3+json")
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 404) {
               log.error("user not found");
            } else if (response.statusCode() != 200) {
               log.error("other error detected");
            }
            return mapper.readValue(
                    response.body(),
                    new TypeReference<>() {
                    }
            );
            }
        catch (Exception e){
            log.error("Failed to fetch repositories for user: " + username, e);
        }
         return null;
        }
}
