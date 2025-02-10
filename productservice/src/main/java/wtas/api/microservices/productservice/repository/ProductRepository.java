package wtas.api.microservices.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import wtas.api.microservices.productservice.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
