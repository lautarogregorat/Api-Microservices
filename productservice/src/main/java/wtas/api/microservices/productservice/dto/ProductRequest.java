package wtas.api.microservices.productservice.dto;

import java.math.BigDecimal;

public record ProductRequest (String name, String description, String skuCode, BigDecimal price) {

}
