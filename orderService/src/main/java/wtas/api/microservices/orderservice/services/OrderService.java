package wtas.api.microservices.orderservice.services;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wtas.api.microservices.orderservice.client.InventoryClient;
import wtas.api.microservices.orderservice.dto.OrderRequest;
import wtas.api.microservices.orderservice.event.OrderPlacedEvent;
import wtas.api.microservices.orderservice.model.Order;
import wtas.api.microservices.orderservice.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

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

            // Ensure all fields are non-null
            if (order.getOrderNumber() == null || orderRequest.userDetails().email() == null ||
                    orderRequest.userDetails().firstName() == null || orderRequest.userDetails().lastName() == null) {
                throw new IllegalArgumentException("Todos los campos del evento OrderPlacedEvent deben estar inicializados");
            }

            var orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(), orderRequest.userDetails().email(),
                    orderRequest.userDetails().firstName(), orderRequest.userDetails().lastName());
            log.info("Start- Sending OrderPlacedEvent {} to Kafka Topic", orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("End- Sending OrderPlacedEvent {} to Kafka Topic", orderPlacedEvent);
        } else {
            throw new RuntimeException("El producto con código: " + orderRequest.skuCode() +
                    " no tiene suficiente stock para la cantidad solicitada: " + orderRequest.quantity());
        }
    }
}