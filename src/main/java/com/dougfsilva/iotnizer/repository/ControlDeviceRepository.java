package com.dougfsilva.iotnizer.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.dougfsilva.iotnizer.config.mongo.ControlDeviceCodec;
import com.dougfsilva.iotnizer.config.mongo.MongoConnection;
import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;

@Repository
public class ControlDeviceRepository {
	
    @Value("${mongodb.database}")
    private String database;

	private final MongoConnection connection;

	public ControlDeviceRepository(MongoConnection connection) {
		this.connection = connection;
	}

	public String create(ControlDevice device) {
		InsertOneResult insertOneResult = getCollection().insertOne(device);
		connection.close();
		return insertOneResult.getInsertedId().asObjectId().getValue().toHexString();
	}

	public void delete(ControlDevice device) {
		getCollection().deleteOne(Filters.eq(new ObjectId(device.getId())));
		connection.close();
	}

	public ControlDevice update(ControlDevice device) {
		ControlDevice updatedDevice = (getCollection().findOneAndUpdate(Filters.eq(new ObjectId(device.getId())),
				Updates.combine(Updates.set("deviceType", device.getDeviceType()), Updates.set("tag", device.getTag()),
						Updates.set("location", device.getLocation()), 
						Updates.set("mqttTopic", device.getMqttTopic())),
				new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)));
		connection.close();
		return updatedDevice;
	}

	public Optional<ControlDevice> findById(String id) {
		Optional<ControlDevice> device = Optional
				.ofNullable(getCollection().find(Filters.eq(new ObjectId(id))).first());
		connection.close();
		return device;
	}

	public List<ControlDevice> findAllByUser(User user) {
		List<ControlDevice> devices = new ArrayList<>();
		MongoCursor<ControlDevice> mongoCursor = getCollection().find(Filters.eq("user_id", user.getId()))
				.batchSize(10000).iterator();
		mongoCursor.forEachRemaining(cursor -> devices.add(cursor));
		connection.close();
		return devices;
	}

	public List<ControlDevice> findAll() {
		List<ControlDevice> devices = new ArrayList<>();
		MongoCursor<ControlDevice> mongoCursor = getCollection().find().batchSize(10000).iterator();
		mongoCursor.forEachRemaining(cursor -> devices.add(cursor));
		connection.close();
		return devices;
	}

	private MongoCollection<ControlDevice> getCollection() {
        return connection.connect(new ControlDeviceCodec()).getClient().getDatabase(database).getCollection("control_device", ControlDevice.class);
    }

}
