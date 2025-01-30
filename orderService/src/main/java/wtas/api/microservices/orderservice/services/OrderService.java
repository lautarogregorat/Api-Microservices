package wtas.api.microservices.orderservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wtas.api.microservices.orderservice.client.InventoryClient;
import wtas.api.microservices.orderservice.dto.OrderRequest;
import wtas.api.microservices.orderservice.model.Order;
import wtas.api.microservices.orderservice.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest) {
        // Validations
        if (!StringUtils.hasText(orderRequest.skuCode())) {
            throw new IllegalArgumentException("El código SKU no debe ser nulo ni vacío");
        }
        if (orderRequest.quantity() == null || orderRequest.quantity() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        // Call to InventoryClient
        boolean isInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (isInStock) {
            // Create and save the order
            Order order = new Order();
            order.setOrderNumber(orderRequest.orderNumber());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            order.setPrice(orderRequest.price());
            orderRepository.save(order);
        } else {
            throw new RuntimeException("El producto con código: " + orderRequest.skuCode() +
                    " no tiene suficiente stock para la cantidad solicitada: " + orderRequest.quantity());
        }
    }
}
