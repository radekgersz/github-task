package org.example.app;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class GithubController {
    private final GithubService githubService;
    public GithubController(GithubService githubService){
        this.githubService = githubService;
    }
    @GetMapping("/api/users/{username}/repos")
    public List<GithubRepository> fetchNonNullRepositories(@PathVariable String username){
        return githubService.getNonForkRepositories(username);
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<ApiErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }
}
