package org.baga.pharmacypoint.services;

import lombok.AllArgsConstructor;
import org.baga.pharmacypoint.models.Discount;
import org.baga.pharmacypoint.repos.DiscountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class DiscountsService {
    private final DiscountsRepository discountsRepository;

    public List<Discount> readAll() {
        return discountsRepository.findAll();
    }

    public Discount read(int id) {
        return discountsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(Discount discount, int id) {
        discount.setId(id);
        discountsRepository.save(discount);
    }

    @Transactional
    public void delete(int id) {
        discountsRepository.deleteById(id);
    }

    @Transactional
    public void save(Discount discount) {
        discountsRepository.save(discount);
    }
}
