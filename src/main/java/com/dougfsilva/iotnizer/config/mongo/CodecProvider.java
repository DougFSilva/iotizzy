package com.dougfsilva.iotnizer.config.mongo;

import com.mongodb.MongoClientSettings;
import lombok.Getter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.springframework.stereotype.Service;

@Getter
@Service
public class CodecProvider {

    private final Codec<Document> codec;

    public CodecProvider() {
        this.codec = MongoClientSettings.getDefaultCodecRegistry().get(Document.class);
    }

}
