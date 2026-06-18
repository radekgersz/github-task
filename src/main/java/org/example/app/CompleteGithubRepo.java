package org.example.app;

import java.util.List;

public record CompleteGithubRepo(
        String owner,
        String name,
        List<Branch> branches,
        boolean isFork
)
{
    public record Branch(
            String name,
            String lastCommitSha
            ){}
}
