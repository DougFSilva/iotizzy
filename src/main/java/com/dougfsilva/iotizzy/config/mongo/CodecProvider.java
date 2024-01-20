package com.dougfsilva.iotizzy.config.mongo;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClientSettings;

import lombok.Getter;

@Getter
@Service
public class CodecProvider {

    private final Codec<Document> codec;

    public CodecProvider() {
        this.codec = MongoClientSettings.getDefaultCodecRegistry().get(Document.class);
    }

}
