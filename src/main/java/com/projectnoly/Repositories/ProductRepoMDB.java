package com.projectnoly.Repositories;

import com.projectnoly.Model.MongoDB.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepoMDB extends MongoRepository<Product,Integer> {
}
