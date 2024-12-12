package org.baga.pharmacypoint.services;

import lombok.AllArgsConstructor;
import org.baga.pharmacypoint.models.Client;
import org.baga.pharmacypoint.repos.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class ClientsService {
    private final ClientsRepository clientsRepository;

    public List<Client> readAll() {
        return clientsRepository.findAll();
    }

    public Client read(int id) {
        return clientsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(Client client, int id) {
        client.setId(id);
        clientsRepository.save(client);
    }

    @Transactional
    public void delete(int id) {
        clientsRepository.deleteById(id);
    }

    @Transactional
    public void save(Client client) {
        clientsRepository.save(client);
    }
}
