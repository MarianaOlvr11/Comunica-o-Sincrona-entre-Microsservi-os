package br.edu.ifsp.productservice.controller;

import br.edu.ifsp.productservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        return productService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(
                        new ErrorResponse("Produto não encontrado", "PRODUCT_NOT_FOUND", id)
                ));
    }

    // Classe interna para resposta de erro padronizada
    record ErrorResponse(String message, String code, Long productId) {}
}
