package org.example.app;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class GithubController {
    private GithubClient githubClient;
    public GithubController(GithubClient githubClient){
        this.githubClient = githubClient;
    }
    @GetMapping("/")
    public String index(){
        List<GithubRepoResponseDTO> response = githubClient.getUserRepositories("Kamczyk09");
        return response.toString();
    }
}
