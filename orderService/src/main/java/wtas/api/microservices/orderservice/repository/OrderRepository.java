package wtas.api.microservices.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wtas.api.microservices.orderservice.model.Order;

public interface OrderRepository  extends JpaRepository<Order, Long> {
}
