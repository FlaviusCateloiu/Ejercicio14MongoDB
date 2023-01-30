package org.example.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.example.entities.Modulo;

import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class ModuloRepositoryImpl implements Repository<Modulo> {

    private MongoCollection<Modulo> collection;
    public ModuloRepositoryImpl(MongoDatabase database) {
        collection = database.getCollection("modulos", Modulo.class);
    }
    @Override
    public List<Modulo> findAll() {
        return (List<Modulo>) collection.find();
    }

    @Override
    public Modulo findOneById(ObjectId id) {
        Modulo modulo = collection.find(eq("_id", id)).first();
        return modulo;
    }

    @Override
    public void save(Modulo modulo) {

    }

    @Override
    public void updateById(ObjectId id) {

    }

    @Override
    public void deleteById(ObjectId id) {

    }
}
