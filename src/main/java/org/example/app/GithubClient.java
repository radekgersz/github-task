package org.example.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@Slf4j
public class GithubClient {
    @Value("${base.url}")
    private String BASE_URL;
    private final RestClient restClient;

    GithubClient(RestClient.Builder builder){
        this.restClient = builder
                .baseUrl(BASE_URL)
                .defaultHeader("Accept", "application/vnd.github+json")
                .build();
    }
    public List<GithubRepository> fetchNonNullRepositories(String username) {
        List<GithubRepoDTO>
        return restClient
                .get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new UserNotFoundException("User not found: " + username);
                })

    }

    public void fetchRepoBranches(String username, String repoName){
    }
}
