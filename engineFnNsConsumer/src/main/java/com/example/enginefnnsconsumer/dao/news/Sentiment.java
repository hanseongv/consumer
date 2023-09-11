package com.example.enginefnnsconsumer.dao.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sentiment {
    @JsonProperty("label")
    private String label;
    @JsonProperty("score")
    private Float score;
}
