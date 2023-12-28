package com.dougfsilva.iotnizer.config.mongo;

import com.dougfsilva.iotnizer.model.Email;
import com.dougfsilva.iotnizer.model.Profile;
import com.dougfsilva.iotnizer.model.ProfileType;
import com.dougfsilva.iotnizer.model.User;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import java.util.List;

public class UserCodec implements Codec<User> {

    private Codec<Document> codec;
    public UserCodec() {
        this.codec = new CodecProvider().getCodec();
    }

    @Override
    public void encode(BsonWriter writer, User user, EncoderContext encoderContext) {
        List<Document> profilesDocuments = user.getProfiles()
                .stream()
                .map(profile -> new Document("type", profile.getProfileType().getDescription()).append("authority",
                        "profile.getAuthority()"))
                .toList();
        Document document = new Document();
        document.append("_id", new ObjectId())
                .append("email", user.getEmail().getAddress())
                .append("name", user.getName())
                .append("password", user.getPassword())
                .append("clientMqttPassword", user.getClientMqttPassword())
                .append("blocked", user.getBlocked())
                .append("profiles", profilesDocuments);
        codec.encode(writer, document, encoderContext);

    }

    @Override
    public Class<User> getEncoderClass() {
        return User.class;
    }

    @Override
    public User decode(BsonReader reader, DecoderContext decoderContext) {
        Document document = codec.decode(reader, decoderContext);
        List<Document> mongoProfileDocuments = document.getList("profiles", Document.class);
        List<Profile> mongoProfiles = mongoProfileDocuments
                .stream()
                .map(profileDocument -> new Profile(ProfileType.toEnum(profileDocument.getString("type"))))
                .toList();
        return new User(document.getObjectId("_id").toHexString(),
                new Email(document.getString("email")),
                document.getString("name"),
                document.getString("password"),
                mongoProfiles,
                document.getString("clientMqttPassword"),
                document.getBoolean("blocked")
                );
    }

}