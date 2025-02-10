package wtas.api.microservices.productservice.services;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wtas.api.microservices.productservice.dto.ProductRequest;
import wtas.api.microservices.productservice.dto.ProductResponse;
import wtas.api.microservices.productservice.model.Product;
import wtas.api.microservices.productservice.repository.ProductRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createdProduct(ProductRequest productRequest) {
        Product product = Product
                .builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .skuCode(productRequest.skuCode())
                .price(productRequest.price())
                .build();

        productRepository.save(product);
        log.info("Producto creado correctamente");
        return new ProductResponse(product.getId(), product.getName(), product.getSkuCode() ,product.getDescription(), product.getPrice());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getSkuCode(),
                        product.getDescription(),
                        product.getPrice()
                ))
                .toList();
    }
}
