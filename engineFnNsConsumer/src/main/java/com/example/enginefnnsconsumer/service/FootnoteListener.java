package com.example.enginefnnsconsumer.service;


import com.example.enginefnnsconsumer.dao.Footnote;
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

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class FootnoteListener implements MessageListener<String, String> {
    private final MongoTemplate mongoTemplate;
    private final FindAndModifyOptions findAndModifyOptions;


    @Override
    @SneakyThrows
    @KafkaListener(topics = "Footnote", groupId = "ConsumerA", autoStartup = "true", concurrency = "3")
    public void onMessage(ConsumerRecord<String, String> record) {
        List<Footnote> save = new ObjectMapper().readValue(record.value(), new TypeReference<ArrayList<Footnote>>() {
        });

        for (var n : save) {
            // upsert 내용이 있다면 수정하고, 없다면 인서트함
            Criteria criteria = new Criteria();
            criteria.andOperator(
                    Criteria.where("rcpNo").is(n.getRcpNo()),
                    Criteria.where("corpCode").is(n.getCorpCode()),
                    Criteria.where("stockCode").is(n.getStockCode())

            );

            Query query = new Query(criteria);

            // 인서트할 필드를 정의
            Update update = new Update();
            update.set("bsnsYear", n.getBsnsYear());
            update.set("reprtCode", n.getReprtCode());
            update.set("remark1", n.getRemark1());
            update.set("remark2", n.getRemark2());
            update.set("remark3", n.getRemark3());
            update.set("remark4", n.getRemark4());

            mongoTemplate.findAndModify(query, update, findAndModifyOptions, Footnote.class);
        }
    }
}
