package com.dougfsilva.iotnizer.config.mongo;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import com.dougfsilva.iotnizer.model.MeasuredValue;

public class MeasuredValueCodec implements Codec<MeasuredValue> {

    private Codec<Document> codec;
    
    public MeasuredValueCodec() {
        this.codec = new CodecProvider().getCodec();
    }

    @Override
    public void encode(BsonWriter writer, MeasuredValue value, EncoderContext encoderContext) {
        Document document = new Document();
        document.append("_id", new ObjectId())
                .append("device_id", value.getDevice_id())
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
        return new MeasuredValue(document.getObjectId("_id").toHexString(),
        		document.getString("device_id"),
                document.getDate("timestamp"),
                document.getDouble("value"));
    }

}