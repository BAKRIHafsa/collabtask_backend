package com.CollabTask.Repositories;

import com.CollabTask.models.Board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
	List<Board> findByUsers_Id(Long userId);
}
