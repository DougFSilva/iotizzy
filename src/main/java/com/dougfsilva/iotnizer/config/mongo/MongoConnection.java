package com.dougfsilva.iotnizer.config.mongo;

import java.util.Arrays;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private MongoClient client;

    public MongoConnection connect(Codec<?> codec) {
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromCodecs(codec));
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyToClusterSettings(builder ->  builder.hosts(Arrays.asList(new ServerAddress(host, port))))
                .credential(MongoCredential.createCredential(username,"admin", password.toCharArray()))
                .codecRegistry(codecRegistry)
                .build();
        client = MongoClients.create(clientSettings);
       return this;
    }

    public MongoConnection connect() {
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyToClusterSettings(builder ->  builder.hosts(Arrays.asList(new ServerAddress(host, port))))
                .credential(MongoCredential.createCredential(username,"admin", password.toCharArray()))
                .build();
        client = MongoClients.create(clientSettings);
        return this;
    }

    public void close() {
        client.close();
    }

}
