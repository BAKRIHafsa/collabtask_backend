package com.CollabTask.Services;

import com.CollabTask.models.AppUser;
import com.CollabTask.models.Card;
import com.CollabTask.models.Comment;
import com.CollabTask.Repositories.AppUserRepository;
import com.CollabTask.Repositories.CardRepository;
import com.CollabTask.Repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CardRepository cardRepository;

    public Comment saveComment(Long cardId, Comment comment) {
        Optional<Card> card = cardRepository.findById(cardId);
        if (card.isPresent()) {
            comment.setCard(card.get());
            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Card not found");
        }
    }

    public List<Comment> getCommentsByCardId(Long cardId) {
        return commentRepository.findByCard_Id(cardId);
    }
}
