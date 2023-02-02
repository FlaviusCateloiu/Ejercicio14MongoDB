package org.example.repositories;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.example.entities.Alumno;

import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class AlumnoRepositoryImpl implements Repository<Alumno> {
    private MongoCollection<Alumno> collection;
    public AlumnoRepositoryImpl(MongoDatabase database) {
        collection = database.getCollection("alumnos", Alumno.class);
    }
    @Override
    public FindIterable<Alumno> findAll() {
        return collection.find();
    }

    @Override
    public Alumno findOneById(ObjectId id) {
        return collection.find(eq("_id", id)).first();
    }

    @Override
    public Alumno save(Alumno alumno) {
        collection.insertOne(alumno);
        return alumno;
    }

    @Override
    public Alumno updateById(ObjectId id, Alumno alumno) {
        collection.replaceOne(eq("_id", id), alumno);
        return alumno;
    }

    @Override
    public void deleteById(ObjectId id) {
        collection.deleteOne(eq("_id", id));
    }

    public FindIterable<Alumno> findAllPendientes() {
        return collection.find(and(eq("curso", "2"), eq("curso", 1)));
    }
}
