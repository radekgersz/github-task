package org.example.app;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class GithubController {
    private final GithubService githubService;
    public GithubController(GithubService githubService){
        this.githubService = githubService;
    }
    @GetMapping("/")
    public String index(){
        return null;
    }
}
