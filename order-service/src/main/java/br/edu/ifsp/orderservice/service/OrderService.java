package br.edu.ifsp.orderservice.service;


import br.edu.ifsp.orderservice.model.Order;import br.edu.ifsp.orderservice.model.ProductInfo;import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

    private final RestTemplate restTemplate;
    private final AtomicLong orderIdCounter = new AtomicLong(1000);

    @Value("${product.service.url}")
    private String productServiceUrl;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Order createOrder(Long productId, Integer quantity) {

        System.out.printf("[OrderService] Buscando produto id=%d no product-service...%n", productId);

        String url = productServiceUrl + "/products/" + productId;

        try {
            ResponseEntity<ProductInfo> response = restTemplate.getForEntity(url, ProductInfo.class);
            ProductInfo product = response.getBody();

            if (product == null) {
                throw new RuntimeException("Resposta vazia do product-service");
            }

            System.out.printf("[OrderService] Produto encontrado: %s (R$ %.2f)%n",
                    product.getName(), product.getPrice());

            return new Order(orderIdCounter.getAndIncrement(), productId, quantity, product);

        } catch (HttpClientErrorException.NotFound e) {
            // product-service retornou 404
            throw new ProductNotFoundException(
                    "Produto id=" + productId + " não encontrado no product-service");

        } catch (ResourceAccessException e) {
            // Timeout ou serviço inacessível
            System.err.println("[OrderService] ERRO: product-service inacessível — " + e.getMessage());
            throw new ServiceUnavailableException(
                    "product-service indisponível. Tente novamente mais tarde.");

        } catch (Exception e) {
            System.err.println("[OrderService] ERRO inesperado: " + e.getMessage());
            throw new ServiceUnavailableException("Erro ao processar pedido: " + e.getMessage());
        }
    }

    public static class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String msg) { super(msg); }
    }

    public static class ServiceUnavailableException extends RuntimeException {
        public ServiceUnavailableException(String msg) { super(msg); }
    }
}
