package com.dougfsilva.iotizzy.config.mongo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import com.dougfsilva.iotizzy.model.MeasuredValue;

public class MeasuredValueCodec implements Codec<MeasuredValue> {

    private Codec<Document> codec;
    
    public MeasuredValueCodec() {
        this.codec = new CodecProvider().getCodec();
    }

    @Override
    public void encode(BsonWriter writer, MeasuredValue value, EncoderContext encoderContext) {
        Document document = new Document();
        document.append("_id", new ObjectId())
                .append("timestamp", value.getTimestamp())
                .append("value", value.getValue());
        codec.encode(writer, document, encoderContext);

    }

    @Override
    public Class<MeasuredValue> getEncoderClass() {
        return MeasuredValue.class;
    }

    @Override
    public MeasuredValue decode(BsonReader reader, DecoderContext decoderContext) {
        Document document = codec.decode(reader, decoderContext);
        Instant instant = document.getDate("timestamp").toInstant();
		LocalDateTime timestamp = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
        return new MeasuredValue(document.getObjectId("_id").toHexString(),
                timestamp,
                document.getDouble("value"));
    }

}