package com.dougfsilva.iotnizer.repository;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.dougfsilva.iotnizer.config.mongo.MongoConnection;
import com.dougfsilva.iotnizer.config.mongo.UserCodec;
import com.dougfsilva.iotnizer.model.Email;
import com.dougfsilva.iotnizer.model.User;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    private final MongoConnection connection;

    public UserRepository(MongoConnection connection) {
        this.connection = connection;
    }

    public String create(User user) {
        InsertOneResult insertOneResult = getCollection().insertOne(user);
        connection.close();
        return insertOneResult.getInsertedId().asObjectId().getValue().toHexString();
    }

    public void delete(User user) {
        getCollection().deleteOne(Filters.eq(new ObjectId(user.getId())));
        connection.close();
    }

    public Optional<User> findById(String id) {
        Optional<User> user = Optional
                .ofNullable((User) getCollection().find(Filters.eq(new ObjectId(id))).first());
        connection.close();
        return user;
    }

    public Optional<User> findByEmail(Email email) {
        Optional<User> user = Optional
                .ofNullable((User) getCollection().find(Filters.eq("email", email.getAddress())).first());
        connection.close();
        return user;
    }

    private MongoCollection getCollection() {
        return connection.connect(new UserCodec(), "user", User.class);
    }

}
