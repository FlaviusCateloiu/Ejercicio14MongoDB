package org.example.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
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
        return collection.find(eq("_id", id)).first();
    }

    @Override
    public void save(Modulo modulo) {
        collection.insertOne(modulo);
    }

    @Override
    public void updateById(ObjectId id, Modulo modulo) {
        collection.replaceOne(eq("_id", id), modulo);
    }

    @Override
    public void deleteById(ObjectId id) {
        collection.deleteOne(eq("_id", id));
    }

    public Modulo findByCurso(String curso) {
        return collection.find(eq("curso", curso)).sort(Sorts.descending("horas")).first();
    }
}
