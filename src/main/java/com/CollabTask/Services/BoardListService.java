package com.CollabTask.Services;

import com.CollabTask.models.AppUser;
import com.CollabTask.models.Board;
import com.CollabTask.models.BoardList;
import com.CollabTask.Repositories.BoardRepository;
import com.CollabTask.config.ResourceNotFoundException;
import com.CollabTask.Repositories.AppUserRepository;
import com.CollabTask.Repositories.BoardListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardListService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardListRepository boardListRepository;

    @Autowired
    private AppUserService appUserService;
    @Autowired
    private AppUserRepository appUserRepository;

    public List<BoardList> getListsByBoardId(Long boardId) {
        return boardListRepository.findByBoardId(boardId);
    }

    public BoardList saveBoardList(Long boardId, BoardList boardList) throws Exception {
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            boardList.setBoard(board);
            return boardListRepository.save(boardList);
        } else {
            throw new Exception("Board not found");
        }
    }

    public void deleteBoardList(Long listId) {
        Optional<BoardList> boardList = boardListRepository.findById(listId);
        if (boardList.isPresent()) {
            boardListRepository.deleteById(listId);
        } else {
            throw new IllegalArgumentException("BoardList with ID " + listId + " not found");
        }
    }

    public Board addUserToBoardById(Long boardId, Long userId) throws ResourceNotFoundException {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found"));
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        board.getUsers().add(user);
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
}
