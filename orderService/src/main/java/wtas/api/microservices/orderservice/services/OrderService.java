package wtas.api.microservices.orderservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wtas.api.microservices.orderservice.dto.OrderRequest;
import wtas.api.microservices.orderservice.model.Order;
import wtas.api.microservices.orderservice.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(orderRequest.orderNumber());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());
        order.setPrice(orderRequest.price());

        orderRepository.save(order);

    }
}
