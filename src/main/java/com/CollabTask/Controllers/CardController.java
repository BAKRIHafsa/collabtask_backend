package com.CollabTask.Controllers;

import com.CollabTask.models.BoardList;
import com.CollabTask.models.Card;
import com.CollabTask.Repositories.BoardListRepository;
import com.CollabTask.Repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users/{userId}/boards/{boardId}/lists/{listId}/cards")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BoardListRepository boardListRepository;

    // Endpoint to get all cards for a specific list
    @GetMapping
    public List<Card> getAllCards(@PathVariable Long listId) {
        Optional<BoardList> boardList = boardListRepository.findById(listId);
        if (boardList.isPresent()) {
            return cardRepository.findByList_Id(listId);
        } else {
            throw new RuntimeException("Board list not found");
        }
    }

    // Endpoint to get a card by its ID
    @GetMapping("/{id}")
    public Optional<Card> getCardById(@PathVariable Long listId, @PathVariable Long id) {
        Optional<BoardList> boardList = boardListRepository.findById(listId);
        if (boardList.isPresent()) {
            return cardRepository.findByIdAndList_Id(id, listId);
        } else {
            throw new RuntimeException("Board list not found");
        }
    }

    // Endpoint to update a card
    @PutMapping("/{id}")
    public Card updateCard(@PathVariable Long listId, @PathVariable Long id, @RequestBody Card card) {
        Optional<BoardList> boardList = boardListRepository.findById(listId);
        if (boardList.isPresent()) {
            return cardRepository.save(card);
        } else {
            throw new RuntimeException("Board list not found");
        }
    }

    // Endpoint to create a new card
    @PostMapping
    public Card createCard(@PathVariable Long listId, @RequestBody Card card) {
        Optional<BoardList> boardList = boardListRepository.findById(listId);
        if (boardList.isPresent()) {
            card.setList(boardList.get());
            return cardRepository.save(card);
        } else {
            throw new RuntimeException("Board list not found");
        }
    }
}
