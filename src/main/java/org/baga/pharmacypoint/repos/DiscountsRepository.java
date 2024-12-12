package org.baga.pharmacypoint.repos;

import org.baga.pharmacypoint.models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountsRepository extends JpaRepository<Discount, Integer> {
}
