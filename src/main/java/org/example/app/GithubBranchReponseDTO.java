package org.example.app;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubBranchReponseDTO(
        @JsonProperty("name")
        String branchName,
        @JsonProperty("commit")
        CommitInfo lastCommitSha
)
{
    public record CommitInfo(
            @JsonProperty("sha")
            String sha
    ){}
}
