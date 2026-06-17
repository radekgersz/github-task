package org.example.app;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GithubController {
    @GetMapping("/")
    public String index(){
        return "test";
    }
}
