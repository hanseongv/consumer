package com.example.enginefnnsconsumer.service;


import com.example.enginefnnsconsumer.dao.news.News;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
@RequiredArgsConstructor
public class NewsListener implements MessageListener<String, String> {
    private final MongoTemplate mongoTemplate;
    private final FindAndModifyOptions findAndModifyOptions;


    @Override
    @SneakyThrows
    @KafkaListener(topics = "News", groupId = "ConsumerA", autoStartup = "true", concurrency = "3")
    // 동시성 3개 생성 옵션 추가. 자동시작 옵션 추가.
    public void onMessage(ConsumerRecord<String, String> record) {
        System.out.println(LocalDate.now());
        List<News> news = new ObjectMapper().readValue(record.value(), new TypeReference<ArrayList<News>>() {
        });
        // bulk 인서트라고 함. 대량 랜덤 입력.
        // 중복될일이 없는 저장일 경우 단순히 save 해도 됨.
        // 뉴스 데이터의 경우 중복이 이루어질 수 있어. mongotemplate 사용.
        // newsRepository.saveAll(news);

        // 스프링 문서 참조
        // https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#reference
        for (News n : news) {
            // upsert 내용이 있다면 수정하고, 없다면 인서트함
            Criteria criteria = new Criteria();
            criteria.andOperator(
                    Criteria.where("createdAt").is(n.getCreatedAt()),
                    Criteria.where("newsId").is(n.getNewsId())
            );

            Query query = new Query(criteria);

            // 인서트할 필드를 정의
            Update update = new Update();
            update.set("createdAt", n.getCreatedAt());
            update.set("newsId", n.getNewsId());
            update.set("title", n.getTitle());
            update.set("content", n.getContent());
            update.set("publishedAt", n.getPublishedAt());
            update.set("envelopedAt", n.getEnvelopedAt());
            update.set("dateline", n.getDateline());
            update.set("provider", n.getProvider());
            update.set("providerLinkPage", n.getProviderLinkPage());
            update.set("stockCode", n.getStockCode());
            update.set("sentimentContent", n.getSentimentContent());

            // findAndModify
            mongoTemplate.findAndModify(query, update, findAndModifyOptions, News.class);
        }


    }
}
