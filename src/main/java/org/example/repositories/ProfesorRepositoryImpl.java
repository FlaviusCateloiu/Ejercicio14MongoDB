package org.example.repositories;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.example.entities.Modulo;
import org.example.entities.Profesor;

import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class ProfesorRepositoryImpl implements Repository<Profesor> {
    private MongoCollection<Profesor> collection;
    public ProfesorRepositoryImpl(MongoDatabase database) {
        collection = database.getCollection("profesores", Profesor.class);
    }
    @Override
    public FindIterable<Profesor> findAll() {
        return collection.find();
    }

    @Override
    public Profesor findOneById(ObjectId id) {
        return collection.find(eq("_id", id)).first();
    }

    @Override
    public Profesor save(Profesor profesor) {
        collection.insertOne(profesor);
        return profesor;
    }

    @Override
    public Profesor updateById(ObjectId id, Profesor profesor) {
        collection.replaceOne(eq("_id", id), profesor);
        return profesor;
    }

    @Override
    public void deleteById(ObjectId id) {
        collection.deleteOne(eq("_id", id));
    }
}
