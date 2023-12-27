package com.dougfsilva.iotnizer.config.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@NoArgsConstructor
@Getter
@Service
public class MongoConnection {

    @Value("${mongodb.host}")
    private String host;

    @Value("${mongodb.port}")
    private Integer port;

    @Value("${mongodb.username}")
    private String username;

    @Value("${mongodb.password}")
    private String password;

    @Value("${mongodb.database}")
    private String database;

    private MongoClient client;

    public MongoCollection connect(Codec<?> codec, String collectionName, Class<?> clazz) {
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromCodecs(codec));
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyToClusterSettings(builder ->  builder.hosts(Arrays.asList(new ServerAddress(host, port))))
                .credential(MongoCredential.createCredential(username,"admin", password.toCharArray()))
                .codecRegistry(codecRegistry)
                .build();
        client = MongoClients.create(clientSettings);
        return client.getDatabase(database).getCollection(collectionName, clazz);
    }

    public MongoCollection connect(String collectionName) {
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyToClusterSettings(builder ->  builder.hosts(Arrays.asList(new ServerAddress(host, port))))
                .credential(MongoCredential.createCredential(username,"admin", password.toCharArray()))
                .build();
        client = MongoClients.create(clientSettings);
        return client.getDatabase(database).getCollection(collectionName);
    }

    public void close() {
        client.close();
    }

}
