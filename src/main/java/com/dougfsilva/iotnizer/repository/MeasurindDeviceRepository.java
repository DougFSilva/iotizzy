package com.dougfsilva.iotnizer.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.dougfsilva.iotnizer.config.mongo.MeasuringDeviceCodec;
import com.dougfsilva.iotnizer.config.mongo.MongoConnection;
import com.dougfsilva.iotnizer.model.MeasuringDevice;
import com.dougfsilva.iotnizer.model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;

@Repository
public class MeasurindDeviceRepository {

	@Value("${mongodb.database}")
	private String database;

	private final MongoConnection connection;

	public MeasurindDeviceRepository(MongoConnection connection) {
		this.connection = connection;
	}

	public String create(MeasuringDevice device) {
		InsertOneResult insertOneResult = getCollection().insertOne(device);
		connection.close();
		return insertOneResult.getInsertedId().asObjectId().getValue().toHexString();
	}

	public void delete(MeasuringDevice device) {
		getCollection().deleteOne(Filters.eq(new ObjectId(device.getId())));
		connection.close();
	}

	public MeasuringDevice update(MeasuringDevice device) {
		MeasuringDevice updatedDevice = (getCollection().findOneAndUpdate(
				Filters.eq(new ObjectId(device.getId())),
				Updates.combine(Updates.set("tag", device.getTag()), Updates.set("location", device.getLocation())),
				new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)));
		connection.close();
		return updatedDevice;
	}

	public Optional<MeasuringDevice> findById(String id) {
		Optional<MeasuringDevice> device = Optional
				.ofNullable((MeasuringDevice) getCollection().find(Filters.eq(new ObjectId(id))).first());
		connection.close();
		return device;
	}

	public List<MeasuringDevice> findAllByUser(User user) {
		List<MeasuringDevice> devices = new ArrayList<>();
		MongoCursor<MeasuringDevice> mongoCursor = getCollection().find(Filters.eq("user_id", user.getId()))
				.batchSize(10000).iterator();
		mongoCursor.forEachRemaining(cursor -> devices.add(cursor));
		connection.close();
		return devices;
	}

	public List<MeasuringDevice> findAll() {
		List<MeasuringDevice> devices = new ArrayList<>();
		MongoCursor<MeasuringDevice> mongoCursor = getCollection().find().batchSize(10000).iterator();
		mongoCursor.forEachRemaining(cursor -> devices.add(cursor));
		connection.close();
		return devices;
	}

	public void createCollection(String collectionName) {
		MongoDatabase mongoDatabase = connection.connect().getClient().getDatabase(database);
		mongoDatabase.createCollection(collectionName,
				new CreateCollectionOptions().capped(true).sizeInBytes(5000000).maxDocuments(20000));
		mongoDatabase.getCollection(collectionName).createIndex(Indexes.ascending("device_id"));
		connection.close();
	}

	public void deleteCollection(String collectionName) {
		connection.connect().getClient().getDatabase(database).getCollection(collectionName).drop();
		connection.close();
	}

	private MongoCollection<MeasuringDevice> getCollection() {
		return connection.connect(new MeasuringDeviceCodec()).getClient().getDatabase(database).getCollection("measuring_device", MeasuringDevice.class);
	}

}
