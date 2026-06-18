package org.example.app;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.util.List;

@Component
@Slf4j
public class GithubClient {
    @Value("${base.url}")
    private String BASE_URL;
    private final ObjectMapper mapper = new ObjectMapper();
    private final RestClient restClient;

    GithubClient(RestClient.Builder builder){
        this.restClient = builder
                .baseUrl(BASE_URL)
                .defaultHeader("Accept", "application/vnd.github+json")
                .build();
    }
    public List<GithubRepoResponseDTO> fetchUserRepositories(String username) {
        return null;
    }

    public void fetchRepoBranches(String username, String repoName){
    }
}
