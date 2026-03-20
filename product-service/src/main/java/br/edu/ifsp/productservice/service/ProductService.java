package br.edu.ifsp.productservice.service;


import br.edu.ifsp.productservice.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    // Simula um banco de dados em memória
    private static final Map<Long, Product> PRODUCTS = new HashMap<>();

    static {
        PRODUCTS.put(1L, new Product(1L, "Notebook Gamer",    "Notebook com RTX 4060, 16GB RAM",  4999.99, 15));
        PRODUCTS.put(2L, new Product(2L, "Mouse Sem Fio",     "Mouse ergonômico 2.4GHz",           149.90,  80));
        PRODUCTS.put(3L, new Product(3L, "Teclado Mecânico",  "Switch Blue, ABNT2",                349.00,  40));
        PRODUCTS.put(4L, new Product(4L, "Monitor 27\"",      "IPS 165Hz FHD",                    1799.00,  20));
        PRODUCTS.put(5L, new Product(5L, "Headset Gamer",     "7.1 Surround, microfone retrátil",  299.90,  60));
    }

    // Delay configurável
    @Value("${product.response.delay-ms:0}")
    private int delayMs;

    public Optional<Product> findById(Long id) {
        if (delayMs > 0) {
            try {
                System.out.printf("[ProductService] Simulando delay de %dms para produto id=%d%n", delayMs, id);
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return Optional.ofNullable(PRODUCTS.get(id));
    }
}
