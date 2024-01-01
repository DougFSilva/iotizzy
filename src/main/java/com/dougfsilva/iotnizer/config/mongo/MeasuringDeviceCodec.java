package com.dougfsilva.iotnizer.config.mongo;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import com.dougfsilva.iotnizer.model.MeasuringDevice;

public class MeasuringDeviceCodec implements Codec<MeasuringDevice> {

    private Codec<Document> codec;
    
    public MeasuringDeviceCodec() {
        this.codec = new CodecProvider().getCodec();
    }

    @Override
    public void encode(BsonWriter writer, MeasuringDevice device, EncoderContext encoderContext) {
        Document document = new Document();
        document.append("_id", new ObjectId())
                .append("user_id", device.getUser_id())
                .append("tag", device.getTag())
                .append("location", device.getLocation())
                .append("mqttTopic", device.getMqttTopic());
        codec.encode(writer, document, encoderContext);

    }

    @Override
    public Class<MeasuringDevice> getEncoderClass() {
        return MeasuringDevice.class;
    }

    @Override
    public MeasuringDevice decode(BsonReader reader, DecoderContext decoderContext) {
        Document document = codec.decode(reader, decoderContext);
        return new MeasuringDevice(document.getObjectId("_id").toHexString(),
        		document.getString("user_id"),
                document.getString("tag"),
                document.getString("location"),
                document.getString("mqttTopic"));
    }

}