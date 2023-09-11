package com.example.enginefnnsconsumer.dao.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;

@Getter
@Document(collection = "News")
public class News {
    @JsonProperty("created_at")
    @Field("createdAt")
    private String createdAt;

    @JsonProperty("news_id")
    @Field("newsId")
    public String newsId;

    @JsonProperty("title")
    @Field("title")
    public String title;

    @JsonProperty("content")
    @Field("content")
    public String content;

    @JsonProperty("published_at")
    @Field("publishedAt")
    public String publishedAt;

    @JsonProperty("enveloped_at")
    @Field("envelopedAt")
    public String envelopedAt;

    @JsonProperty("dateline")
    @Field("dateline")
    public String dateline;

    @JsonProperty("provider")
    @Field("provider")
    public String provider;

    @JsonProperty("provider_link_page")
    @Field("providerLinkPage")
    public String providerLinkPage;

    @JsonProperty("stockCode")
    @Field("stockCode")
    private ArrayList<String> stockCode;

    @JsonProperty("sentimentContent")
    @Field("sentimentContent")
    private ArrayList<SentimentContent> sentimentContent;
}
