package com.example.enginefnnsconsumer.dao.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SentimentContent {
    @JsonProperty("sentiment")
    private Sentiment sentiment;
    @JsonProperty("content")
    private String content;
}
