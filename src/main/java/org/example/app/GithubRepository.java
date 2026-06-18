package org.example.app;

import java.util.List;

public record GithubRepository(
        String name,
        String owner,
        List<Branch> branches
) {}
