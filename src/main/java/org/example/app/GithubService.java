package org.example.app;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GithubService {
    private final GithubClient githubClient;

    public GithubService(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    public List<CompleteGithubRepo> getNonForkRepos(String username) {
        List<CompleteGithubRepo> parsedRepos = new ArrayList<>();
        List<GithubRepoResponseDTO> repos = githubClient.fetchUserRepositories(username);
        for (GithubRepoResponseDTO repo : repos) {
            if (!repo.isFork()) {
                parsedRepos.add(parseRepo(repo));
            }
        }
        return parsedRepos;
    }
    private static CompleteGithubRepo parseRepo(GithubRepoResponseDTO repoResponseDTO){
        return null;
    }
}