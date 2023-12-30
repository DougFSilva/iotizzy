package com.dougfsilva.iotnizer.config.mongo;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.ControlDeviceType;

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
                .append("name", device.getName())
                .append("location", device.getLocation());
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
        		document.getObjectId("user_id").toHexString(),
                ControlDeviceType.toEnum(document.getString("deviceType")),
                document.getString("name"),
                document.getString("location"));
    }

}