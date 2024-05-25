package com.CollabTask.Controllers;

import com.CollabTask.models.Comment;
import com.CollabTask.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users/{userId}/boards/{boardId}/lists/{listId}/cards/{cardId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public Comment createComment(@PathVariable Long userId, @PathVariable Long boardId, @PathVariable Long listId, @PathVariable Long cardId, @RequestBody Comment comment) {
        return commentService.saveComment(cardId, comment);
    }

    @GetMapping("/all")
    public List<Comment> getCommentsByCardId(@PathVariable Long userId, @PathVariable Long boardId, @PathVariable Long listId, @PathVariable Long cardId) {
        return commentService.getCommentsByCardId(cardId);
    }
}
