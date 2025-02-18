package com.projectnoly.Repositories;

import com.projectnoly.Model.MongoDB.SaleProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SaleProductMDB extends MongoRepository<SaleProduct, Integer> {
}
