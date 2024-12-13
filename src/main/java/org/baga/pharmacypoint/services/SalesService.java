package org.baga.pharmacypoint.services;

import lombok.AllArgsConstructor;
import org.baga.pharmacypoint.models.Order;
import org.baga.pharmacypoint.models.Product;
import org.baga.pharmacypoint.models.Sale;
import org.baga.pharmacypoint.repos.ClientsRepository;
import org.baga.pharmacypoint.repos.OrdersRepository;
import org.baga.pharmacypoint.repos.ProductsRepository;
import org.baga.pharmacypoint.repos.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class SalesService {
    private final ProductsRepository productsRepository;
    private final OrdersRepository ordersRepository;
    private final SalesRepository salesRepository;
    private final ClientsRepository clientsRepository;

    @Transactional
    public void buyProduct(int productId, int quantity, int clientId) {
        Product product = productsRepository.findById(productId).orElseThrow(RuntimeException::new);

        if (product.getProductReminder() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("Оплачен");
        order.setClient(clientsRepository.findById(clientId).orElseThrow(RuntimeException::new));
        ordersRepository.save(order);

        Sale sale = new Sale();
        sale.setProduct(product);
        sale.setOrder(order);
        sale.setQuantitySold(quantity);
        salesRepository.save(sale);

        product.setProductReminder(product.getProductReminder() - quantity);
        productsRepository.save(product);
    }
}
