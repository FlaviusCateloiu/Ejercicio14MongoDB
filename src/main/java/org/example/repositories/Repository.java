package org.example.repositories;

import com.mongodb.client.FindIterable;
import org.bson.types.ObjectId;

import java.util.List;

public interface Repository<T> {
    FindIterable<T> findAll();
    T findOneById(ObjectId id);
    T save(T t);
    T updateById(ObjectId id, T t);
    void deleteById(ObjectId id);
}
