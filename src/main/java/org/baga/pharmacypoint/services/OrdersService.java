package org.baga.pharmacypoint.services;

import lombok.AllArgsConstructor;
import org.baga.pharmacypoint.models.Order;
import org.baga.pharmacypoint.repos.ClientsRepository;
import org.baga.pharmacypoint.repos.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final ClientsRepository clientsRepository;


    public List<Order> readAll() {
        return ordersRepository.findAll();
    }

    public Order read(int id) {
        return ordersRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(Order order, int id) {
        order.setId(id);
        ordersRepository.save(order);
    }

    @Transactional
    public void delete(int id) {
        ordersRepository.deleteById(id);
    }

    @Transactional
    public void save(Order order) {
        var client = clientsRepository.findById(1).orElseThrow(RuntimeException::new);
        order.setClient(client);
        ordersRepository.save(order);
        client.getOrders().add(order);
        clientsRepository.save(client);
    }
}
