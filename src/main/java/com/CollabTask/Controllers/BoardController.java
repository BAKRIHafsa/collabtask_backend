package com.CollabTask.Controllers;

import com.CollabTask.models.Board;
import com.CollabTask.Services.BoardService;
import com.CollabTask.config.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users/{userId}/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping
    public ResponseEntity<List<Board>> getAllBoards(@PathVariable Long userId) {
        List<Board> boards = boardService.getAllBoardsByUser(userId);
        if (boards.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoard(@PathVariable Long userId, @PathVariable Long id) {
        Board board = boardService.getBoardByUser(userId, id);
        if (board == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(board);
    }
    

    @PostMapping
    public ResponseEntity<Board> createBoard(@PathVariable Long userId, @RequestBody Board board) {
        try {
            Board savedBoard = boardService.saveBoard(userId, board);
            URI location = URI.create(String.format("/api/users/%d/boards/%d", userId, savedBoard.getId()));
            return ResponseEntity.created(location).body(savedBoard);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    


    @PostMapping("/{boardId}/users/{userIdToAdd}")
    public ResponseEntity<Board> addUserToBoardById(@PathVariable Long boardId, @PathVariable Long userIdToAdd) {
        try {
            Board updatedBoard = boardService.addUserToBoardById(boardId, userIdToAdd);
            return ResponseEntity.ok(updatedBoard);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{boardId}/users/email/{email}")
    public ResponseEntity<Board> addUserToBoardByEmail(@PathVariable Long boardId, @PathVariable String email) {
        try {
            Board updatedBoard = boardService.addUserToBoardByEmail(boardId, email);
            return ResponseEntity.ok(updatedBoard);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
