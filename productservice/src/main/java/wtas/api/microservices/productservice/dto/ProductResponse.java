package wtas.api.microservices.productservice.dto;

import java.math.BigDecimal;

public record ProductResponse(String id, String name, String skuCode, String description, BigDecimal price) {
}
