package org.example.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
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
        List<GithubRepoDTO> repoDTOS = restClient
                .get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .onStatus(status -> status.isSameCodeAs(HttpStatus.NOT_FOUND), (request, response) -> {
                    throw new UserNotFoundException("User not found: " + username);
                })                .body(REPO_LIST_TYPE);

        if (repoDTOS == null || repoDTOS.isEmpty()) {
            log.warn("No repositories found for user: {}", username);
            return Collections.emptyList();
        }
        return repoDTOS.stream()
                .filter(repo -> !repo.isFork())
                .map(repo -> {
                    List<Branch> branches = fetchRepoBranches(repo.owner().login(), repo.name());
                    return new GithubRepository(repo.name(), repo.owner().login(), branches);
                })
                .toList();
    }

    public List<Branch> fetchRepoBranches(String username, String repoName){
        List<GithubBranchDTO> branchDTOS = restClient
                .get()
                .uri("/repos/{username}/{repo}/branches", username, repoName)
                .retrieve()
                .body(BRANCH_LIST_TYPE);
        if (branchDTOS == null || branchDTOS.isEmpty()) {
            log.warn("No branches found for user {} in repository: {}", username, repoName);
            return Collections.emptyList();
        }

        return branchDTOS
                .stream()
                .map(dto -> new Branch(dto.branchName(), dto.CommitInfo().lastSha()))
                .toList();
    }
}
