package com.example.enginefnnsconsumer.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfig {
    @Value("${mongo.dbname}")
    private String MONGO_DBNAME;
    @Value("${mongo.username}")
    private String MONGO_USERNAME;
    @Value("${mongo.password}")
    private String MONGO_PASSWORD;
    @Value("${mongo.server}")
    private String MONGO_SERVER;

    @Bean
    public MongoClient mongoClient() {
        String url = String.format("mongodb+srv://%s:%s@%s/?retryWrites=true&w=majority", this.MONGO_USERNAME, this.MONGO_PASSWORD, this.MONGO_SERVER);
        return MongoClients.create(url);
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(MongoClient mongoClient) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, this.MONGO_DBNAME);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory, MappingMongoConverter mappingMongoConverter) {
        return new MongoTemplate(mongoDatabaseFactory, mappingMongoConverter);
    }

    // _class 안나오게
    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory mongoDatabaseFactory) {

        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return converter;
    }
    // 몽고 Upsert 옵션
    // 찾고 수정할 옵션으로 새로운것이 있다면 넣고, 없으면 업데이트함.
    @Bean
    public FindAndModifyOptions findAndModifyOptions() {
        return new FindAndModifyOptions().returnNew(true).upsert(true);
    }
}
