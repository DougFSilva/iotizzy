package com.dougfsilva.iotizzy.config.mongo;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import com.dougfsilva.iotizzy.model.ControlDevice;
import com.dougfsilva.iotizzy.model.ControlDeviceType;

public class ControlDeviceCodec implements Codec<ControlDevice> {

    private Codec<Document> codec;
    
    public ControlDeviceCodec() {
        this.codec = new CodecProvider().getCodec();
    }

    @Override
    public void encode(BsonWriter writer, ControlDevice device, EncoderContext encoderContext) {
        Document document = new Document();
        document.append("_id", new ObjectId())
                .append("user_id", device.getUser_id())
                .append("deviceType", device.getDeviceType())
                .append("tag", device.getTag())
                .append("location", device.getLocation())
                .append("mqttTopic", device.getMqttTopic());
        codec.encode(writer, document, encoderContext);

    }

    @Override
    public Class<ControlDevice> getEncoderClass() {
        return ControlDevice.class;
    }

    @Override
    public ControlDevice decode(BsonReader reader, DecoderContext decoderContext) {
        Document document = codec.decode(reader, decoderContext);
        return new ControlDevice(document.getObjectId("_id").toHexString(),
        		document.getString("user_id"),
                ControlDeviceType.toEnum(document.getString("deviceType")),
                document.getString("tag"),
                document.getString("location"),
                document.getString("mqttTopic"));
    }

}