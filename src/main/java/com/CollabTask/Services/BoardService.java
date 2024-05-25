package com.CollabTask.Services;

import com.CollabTask.models.AppUser;
import com.CollabTask.models.Board;
import com.CollabTask.Repositories.AppUserRepository;
import com.CollabTask.Repositories.BoardRepository;
import com.CollabTask.config.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppUserService appUserService;

    public Board getBoard(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }
    public List<Board> getAllBoardsByUser(Long userId) {
        Optional<AppUser> user = appUserRepository.findById(userId);
        if (user.isPresent()) {
            return boardRepository.findByUsers_Id(userId);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Board getBoardByUser(Long userId, Long boardId) {
        Optional<Board> board = boardRepository.findById(boardId);
        if (board.isPresent() && board.get().getUsers().stream().anyMatch(user -> user.getId().equals(userId))) {
            return board.get();
        } else {
            throw new RuntimeException("Board not found or user does not have access to this board");
        }
    }

    public Board saveBoard(Long userId, Board board) {
        Optional<AppUser> user = appUserRepository.findById(userId);
        if (user.isPresent()) {
            board.getUsers().add(user.get());
            return boardRepository.save(board);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }

    public Board addUserToBoardByEmail(Long boardId, String email) throws ResourceNotFoundException {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found"));

        AppUser user = appUserService.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        board.getUsers().add(user);
        return boardRepository.save(board);
    }

    public Board addUserToBoardById(Long boardId, Long userIdToAdd) throws ResourceNotFoundException {
        // Retrieve the board entity from the database
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found"));

        // Retrieve the user entity from the database
        AppUser userToAdd = appUserRepository.findById(userIdToAdd)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Add the user to the board's list of users
        board.getUsers().add(userToAdd);

        // Save the updated board entity
        return boardRepository.save(board);
    }
}
