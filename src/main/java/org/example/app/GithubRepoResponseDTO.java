package org.example.app;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubRepoResponseDTO(
        @JsonProperty("name")
        String name,
        @JsonProperty("owner")
        OwnerInfo owner,
        @JsonProperty("fork")
        Boolean isFork
) {
        public record OwnerInfo(
                @JsonProperty("login")
                String login
        ) {}
}
