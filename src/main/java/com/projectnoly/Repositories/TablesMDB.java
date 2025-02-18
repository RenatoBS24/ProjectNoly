package com.projectnoly.Repositories;

import com.projectnoly.Model.MongoDB.Tables;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TablesMDB extends MongoRepository<Tables, Integer> {
}
