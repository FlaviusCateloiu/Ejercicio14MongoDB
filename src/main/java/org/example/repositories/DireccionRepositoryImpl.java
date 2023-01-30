package org.example.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.example.entities.Direccion;
import org.example.entities.Profesor;

import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class DireccionRepositoryImpl implements Repository<Direccion> {
    private MongoCollection<Direccion> collection;
    public DireccionRepositoryImpl(MongoDatabase database) {
        collection = database.getCollection("direcciones", Direccion.class);
    }
    @Override
    public List<Direccion> findAll() {
        return (List<Direccion>) collection.find();
    }

    @Override
    public Direccion findOneById(ObjectId id) {
        return collection.find(eq("_id", id)).first();
    }

    @Override
    public void save(Direccion direccion) {
        collection.insertOne(direccion);
    }

    @Override
    public void updateById(ObjectId id, Direccion direccion) {
        collection.replaceOne(eq("_id", id), direccion);
    }

    @Override
    public void deleteById(ObjectId id) {
        collection.deleteOne(eq("_id", id));
    }
}
