package com.dougfsilva.iotnizer.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.dougfsilva.iotnizer.config.mongo.MongoConnection;
import com.dougfsilva.iotnizer.config.mongo.UserCodec;
import com.dougfsilva.iotnizer.model.Email;
import com.dougfsilva.iotnizer.model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;

@Repository
public class UserRepository {
	
    @Value("${mongodb.database}")
    private String database;

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
    
	public User update(User user) {
		List<Document> profilesDocuments = user.getProfiles().stream()
				.map(profile -> new Document("type", profile.getProfileType().getDescription()).append("authority",
						profile.getAuthority()))
				.collect(Collectors.toList());
		User updatedUser = (getCollection().findOneAndUpdate(Filters.eq(new ObjectId(user.getId())),
				Updates.combine(
						Updates.set("email", user.getEmail().getAddress()),
						Updates.set("name", user.getName()), 
						Updates.set("password", user.getPassword()),
						Updates.set("profiles", profilesDocuments),
						Updates.set("blocked", user.getBlocked())),
				new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)));
		connection.close();
		return updatedUser;
	}

    public Optional<User> findById(String id) {
        Optional<User> user = Optional
                .ofNullable(getCollection().find(Filters.eq(new ObjectId(id))).first());
        connection.close();
        return user;
    }

    public Optional<User> findByEmail(Email email) {
        Optional<User> user = Optional
                .ofNullable(getCollection().find(Filters.eq("email", email.getAddress())).first());
        connection.close();
        return user;
    }
    
    public List<User> findAll() {
		List<User> users = new ArrayList<>();
		MongoCursor<User> mongoCursor = getCollection().find().batchSize(10000).iterator();
		mongoCursor.forEachRemaining(cursor -> users.add(cursor));
		connection.close();
		return users;
	}

	private MongoCollection<User> getCollection() {
        return connection.connect(new UserCodec()).getClient().getDatabase(database).getCollection("user", User.class);
    }

}
