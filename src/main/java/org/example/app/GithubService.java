package org.example.app;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GithubService {
    private final GithubClient githubClient;

    GithubService(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    List<GithubRepository> getNonForkRepositories(String username) {
        return githubClient.fetchNonNullRepositories(username);
    }
}