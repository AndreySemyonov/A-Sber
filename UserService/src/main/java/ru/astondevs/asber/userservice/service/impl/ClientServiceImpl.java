package ru.astondevs.asber.userservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.userservice.entity.Client;
import ru.astondevs.asber.userservice.entity.enums.ClientStatus;
import ru.astondevs.asber.userservice.repository.ClientRepository;
import ru.astondevs.asber.userservice.service.ClientService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of {@link ClientService}.
 * Works with {@link ClientRepository}.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    /**
     * {@inheritDoc}
     * For saving client using method {@link ClientRepository#save(Object)}.
     *
     * @param client Credit id from {@link Client}
     * @return {@link Client}
     */
    @Override
    @Transactional
    public Client save(Client client) {
        log.info("Save client in DB: {}", client.getId());
        client.setDateAccession(LocalDateTime.now());
        client.setClientStatus(ClientStatus.NOT_ACTIVE);
        Client clientToSave = clientRepository.save(client);

        log.info("Client saved: {}", client.getId());
        return clientToSave;
    }

    /**
     * {@inheritDoc}
     * For getting client using method {@link ClientRepository#getClientWithContactsAndUserProfile(UUID)}.
     *
     * @return {@link Client}
     * @throws EntityNotFoundException if client not found
     */
    @Override
    public Client getClientWithContactsAndUserProfile(UUID id) {
        log.info("Getting client with contacts by id: {}", id);
        Client client = clientRepository.getClientWithContactsAndUserProfile(id)
                .orElseThrow(() -> new EntityNotFoundException("not found client with id = " + id));

        log.info("Client with contacts found: {}", id);
        return client;
    }

    /**
     * {@inheritDoc}
     * For getting client with fingerprint from db uses method {@link ClientRepository#getClientWithFingerprint(UUID)}.
     *
     * @return {@link Client}
     * @throws EntityNotFoundException if client not found
     */
    @Override
    public Client getClientWithFingerprint(UUID id) {
        log.info("Getting client with fingerprint by id: {}", id);
        Client client = clientRepository.getClientWithFingerprint(id)
                .orElseThrow(() -> new EntityNotFoundException("not found client with id = " + id));

        log.info("Client with fingerprint found: {}", id);
        return client;
    }
}
