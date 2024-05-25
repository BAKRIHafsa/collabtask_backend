package com.CollabTask.Services;


import com.CollabTask.models.Card;
import com.CollabTask.Repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Optional<Card> getCardById(Long id) {
        return cardRepository.findById(id);
    }
    public Card createCard(Card card) {
        // Add any additional logic here before saving the card
        return cardRepository.save(card);
    }

    public Card updateCard(Card card) {
        return cardRepository.save(card);
    }
}
