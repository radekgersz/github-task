package org.example.app;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubBranchDTO(
        @JsonProperty("name")
        String branchName,
        @JsonProperty("commit")
        CommitInfo CommitInfo
)
{
    public record CommitInfo(
            @JsonProperty("sha")
            String lastSha
    ){}
}
