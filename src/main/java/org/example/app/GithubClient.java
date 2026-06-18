package org.example.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class GithubClient {
    private final RestClient restClient;
    private final ParameterizedTypeReference<List<GithubRepoDTO>> REPO_LIST_TYPE = new ParameterizedTypeReference<>() {};
    private final ParameterizedTypeReference<List<GithubBranchDTO>> BRANCH_LIST_TYPE = new ParameterizedTypeReference<>() {};

    GithubClient(RestClient.Builder builder, @Value("${base.url}") String BASE_URL){
        this.restClient = builder
                .baseUrl(BASE_URL)
                .defaultHeader("Accept", "application/vnd.github+json")
                .build();
    }
    public List<GithubRepository> fetchNonNullRepositories(String username) {
        //fetch all Repositories
        List<GithubRepoDTO> repoDTOS = restClient
                .get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new UserNotFoundException("User not found: " + username);
                })
                .body(REPO_LIST_TYPE);

        if (repoDTOS == null) {
            return null;
        }
        List<GithubRepository> nonForkRepos = new ArrayList<>();
        for (GithubRepoDTO repo : repoDTOS) {
            if (!repo.isFork()) {
                List<Branch> branches = fetchRepoBranches(repo.owner().login(), repo.name());
                nonForkRepos.add(new GithubRepository(repo.name(), repo.owner().login(), branches));
            }
        }
        return nonForkRepos;
    }



    public List<Branch> fetchRepoBranches(String username, String repoName){
        List<GithubBranchDTO> branchDTOS = restClient
                .get()
                .uri("/repos/{username}/{repo}/branches", username, repoName)
                .retrieve()
                .body(BRANCH_LIST_TYPE);

        return branchDTOS
                .stream()
                .map(dto -> new Branch(dto.branchName(), dto.CommitInfo().lastSha()))
                .toList();
    }
}
