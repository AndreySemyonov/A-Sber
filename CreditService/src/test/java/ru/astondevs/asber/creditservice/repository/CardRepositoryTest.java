package ru.astondevs.asber.creditservice.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.astondevs.asber.creditservice.ExternalSystemConfig;
import ru.astondevs.asber.creditservice.entity.Card;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
class CardRepositoryTest {

    @Autowired
    private CardRepository cardRepository;

    @Test
    @DisplayName("If user wants to get list of card by client id then return it")
    void findCardsByClientId_shouldReturnListOfCards() {
        UUID clientId = UUID.fromString("798060ff-ed9b-4745-857c-a7a2f4a2e3a2");
        List<Card> cardsByClientId = cardRepository.findCardsByClientId(clientId);
        assertEquals(1, cardsByClientId.size());
    }

    @Test
    @DisplayName("If user wants to get list of card by client id then return empty list")
    void findCardsByClientId_shouldReturnEmptyListOfCards() {
        UUID clientId = UUID.randomUUID();
        List<Card> cardsByClientId = cardRepository.findCardsByClientId(clientId);
        assertEquals(0, cardsByClientId.size());
    }

    @Test
    @DisplayName("If user wants to get card by card number then return it")
    void findCardByCardNumber_shouldReturnCard() {
        String cardNumber = "408181057000011";
        Optional<Card> card = cardRepository.findCardByCardNumber(cardNumber);
        assertDoesNotThrow(card::get);
        assertEquals("IVAN IVANOV", card.get().getHolderName());
    }

    @Test
    @DisplayName("If user wants to get card by non existing card number then throw no such element exception")
    void findCardByCardNumber_shouldThrowNoSuchElementException() {
        String cardNumber = "123123123123";
        Optional<Card> card = cardRepository.findCardByCardNumber(cardNumber);
        assertThrows(NoSuchElementException.class, card::get);
    }

    @Test
    @DisplayName("If user wants to get card by card id then return it")
    void findCardByCardId_shouldReturnCard() {
        UUID cardId = UUID.fromString("46561a3e-6e80-11ed-a1eb-0242ac120001");
        Optional<Card> cardByCardId = cardRepository.findCardByCardId(cardId);
        assertDoesNotThrow(cardByCardId::get);
        assertEquals("IVAN IVANOV", cardByCardId.get().getHolderName());
    }

    @Test
    @DisplayName("If user wants to get card by card id then throw no such element exception")
    void findCardByCardId_shouldThrowNoSuchElementException() {
        UUID cardId = UUID.randomUUID();
        Optional<Card> cardByCardId = cardRepository.findCardByCardId(cardId);
        assertThrows(NoSuchElementException.class, cardByCardId::get);
    }

    @Test
    @DisplayName("If user wants to get full card info by credit id then return it")
    void getFullCardInfoByCreditId_shouldReturnCard() {
        UUID creditId = UUID.fromString("9093905a-6e6b-11ed-a1eb-0242ac120001");
        Optional<Card> fullCardInfoByCreditId = cardRepository.getFullCardInfoByCreditId(creditId);
        assertDoesNotThrow(fullCardInfoByCreditId::get);
        assertEquals("IVAN IVANOV", fullCardInfoByCreditId.get().getHolderName());
    }

    @Test
    @DisplayName("If user wants to get full card info by credit id then return it")
    void getFullCardInfoByCreditId_shouldThrowNoSuchElementException() {
        UUID creditId = UUID.randomUUID();
        Optional<Card> fullCardInfoByCreditId = cardRepository.getFullCardInfoByCreditId(creditId);
        assertThrows(NoSuchElementException.class, fullCardInfoByCreditId::get);
    }
}