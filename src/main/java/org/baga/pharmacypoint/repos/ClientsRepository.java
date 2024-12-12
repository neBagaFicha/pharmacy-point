package org.baga.pharmacypoint.repos;

import org.baga.pharmacypoint.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository<Client, Integer> {
}
