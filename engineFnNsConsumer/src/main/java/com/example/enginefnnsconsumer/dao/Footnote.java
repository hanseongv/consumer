package com.example.enginefnnsconsumer.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document(collection = "Footnote")
public class Footnote {
    @JsonProperty("rcpNo")
    @Field("rcpNo")
    String rcpNo;
    @JsonProperty("corp_code")
    @Field("corpCode")
    String corpCode;
    @JsonProperty("stock_code")
    @Field("stockCode")
    String stockCode;
    @JsonProperty("bsns_year")
    @Field("bsnsYear")
    String bsnsYear;
    @JsonProperty("reprt_code")
    @Field("reprtCode")
    String reprtCode;
    @JsonProperty("remark1")
    @Field("remark1")
    String remark1;
    @JsonProperty("remark2")
    @Field("remark2")
    String remark2;
    @JsonProperty("remark3")
    @Field("remark3")
    String remark3;
    @JsonProperty("remark4")
    @Field("remark4")
    String remark4;
}
