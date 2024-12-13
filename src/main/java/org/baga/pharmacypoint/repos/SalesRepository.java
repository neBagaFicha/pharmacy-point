package org.baga.pharmacypoint.repos;

import org.baga.pharmacypoint.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Sale, Integer> {
}
