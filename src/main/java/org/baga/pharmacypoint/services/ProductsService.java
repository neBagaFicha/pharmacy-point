package org.baga.pharmacypoint.services;

import lombok.AllArgsConstructor;
import org.baga.pharmacypoint.models.Product;
import org.baga.pharmacypoint.repos.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class ProductsService {
    private final ProductsRepository productsRepository;

    public List<Product> readAll() {
        return productsRepository.findAll();
    }

    public Product read(int id) {
        return productsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(Product product, int id) {
        product.setId(id);
        productsRepository.save(product);
    }

    @Transactional
    public void delete(int id) {
        productsRepository.deleteById(id);
    }

    @Transactional
    public void save(Product product) {
        productsRepository.save(product);
    }
}
