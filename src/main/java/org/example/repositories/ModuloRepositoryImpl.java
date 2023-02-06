package org.example.repositories;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.bson.types.ObjectId;
import org.example.entities.Alumno;
import org.example.entities.Modulo;
import org.example.entities.Profesor;

import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class ModuloRepositoryImpl implements Repository<Modulo> {

    private MongoCollection<Modulo> collection;
    public ModuloRepositoryImpl(MongoDatabase database) {
        collection = database.getCollection("modulos", Modulo.class);
    }
    @Override
    public FindIterable<Modulo> findAll() {
        return collection.find();
    }

    @Override
    public Modulo findOneById(ObjectId id) {
        return collection.find(eq("_id", id)).first();
    }

    @Override
    public Modulo save(Modulo modulo) {
        collection.insertOne(modulo);
        return modulo;
    }

    @Override
    public Modulo updateById(Modulo modulo) {
        collection.replaceOne(eq("_id", modulo.getId()), modulo);
        return modulo;
    }

    @Override
    public void deleteById(ObjectId id) {
        collection.deleteOne(eq("_id", id));
    }

    public FindIterable<Modulo> findByCurso(String curso) {
        return collection.find(eq("curso", curso)).sort(Sorts.descending("horas"));
    }
}
