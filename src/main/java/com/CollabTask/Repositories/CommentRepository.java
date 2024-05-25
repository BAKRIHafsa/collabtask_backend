package com.CollabTask.Repositories;

import com.CollabTask.models.Comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCard_Id(Long cardId);


}
