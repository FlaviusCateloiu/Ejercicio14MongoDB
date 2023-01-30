package org.example.repositories;

import org.bson.types.ObjectId;

import java.util.List;

public interface Repository<T> {
    List<T> findAll();
    T findOneById(ObjectId id);
    void save(T t);
    void updateById(ObjectId id);
    void deleteById(ObjectId id);
}
