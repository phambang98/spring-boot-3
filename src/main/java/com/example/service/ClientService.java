package com.example.service;

import com.example.entity.Client;
import com.example.enums.Provider;
import com.example.model.CustomOAuth2Client;
import com.example.model.RecordNotFoundException;
import com.example.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        List<Client> clientList = clientRepository.findAll();

        if (clientList.size() > 0) {
            return clientList;
        } else {
            return new ArrayList<>();
        }
    }

    public Client getClientById(Long id) throws RecordNotFoundException {
        Optional<Client> client = clientRepository.findById(id);

        if (client.isPresent()) {
            return client.get();
        } else {
            throw new RecordNotFoundException("No client record exist for given id");
        }
    }

    public Client createOrUpdateClient(Client entity) {
        if (entity.getId() == null) {
            return clientRepository.save(entity);
        }
        Optional<Client> client = clientRepository.findById(entity.getId());
        Client newEntity = client.get();
        newEntity.setEmail(entity.getEmail());
        newEntity.setFirstName(entity.getFirstName());
        newEntity.setLastName(entity.getLastName());
        newEntity = clientRepository.save(newEntity);
        return newEntity;
    }

    public void deleteClientById(Long id) throws RecordNotFoundException {
        Optional<Client> client = clientRepository.findById(id);

        if (client.isPresent()) {
            clientRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No client record exist for given id");
        }
    }

    public void processOAuthPostLogin(CustomOAuth2Client customOAuth2Client) {
        Client existClient = clientRepository.findByUserName(customOAuth2Client.getName());

        if (existClient == null) {
            Client client = new Client();
            Map<String, Object> mapAttr = customOAuth2Client.getAttributes();
            client.setUserName((String) mapAttr.getOrDefault("sub", customOAuth2Client.getEmail()));
            client.setFirstName((String) mapAttr.getOrDefault("family_name", null));
            client.setLastName((String) mapAttr.getOrDefault("given_name", null));
            client.setProvider(Provider.GOOGLE.name());
            client.setStatus("A");
            client.setEmail(customOAuth2Client.getEmail());
            clientRepository.save(client);

            System.out.println("Created new user: " + customOAuth2Client.getName());
        }

    }
}