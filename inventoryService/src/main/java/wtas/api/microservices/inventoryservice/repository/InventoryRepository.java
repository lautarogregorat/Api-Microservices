package wtas.api.microservices.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wtas.api.microservices.inventoryservice.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(String skuCode, Integer quantity);
}
