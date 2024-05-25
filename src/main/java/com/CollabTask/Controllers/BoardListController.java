package com.CollabTask.Controllers;

import com.CollabTask.models.BoardList;
import com.CollabTask.Services.BoardListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users/{userId}/boards/{boardId}/lists")
public class BoardListController {

    @Autowired
    private BoardListService boardListService;

    @GetMapping
    public ResponseEntity<List<BoardList>> getListsByBoardId(@PathVariable Long userId, @PathVariable Long boardId) {
        try {
            List<BoardList> lists = boardListService.getListsByBoardId(boardId);
            return ResponseEntity.ok(lists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<BoardList> createBoardList(@PathVariable Long userId, @PathVariable Long boardId, @RequestBody BoardList boardList) {
        try {
            BoardList createdList = boardListService.saveBoardList(boardId, boardList);
            URI location = URI.create(String.format("/api/users/%d/boards/%d/lists/%d", userId, boardId, createdList.getId()));
            return ResponseEntity.created(location).body(createdList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @DeleteMapping("/{listId}")
    public ResponseEntity<Void> deleteBoardList(@PathVariable Long userId, @PathVariable Long boardId, @PathVariable Long listId) {
        try {
            boardListService.deleteBoardList(listId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
