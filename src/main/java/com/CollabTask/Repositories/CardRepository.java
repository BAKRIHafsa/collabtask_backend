package com.CollabTask.Repositories;

import com.CollabTask.models.Card;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
	Optional<Card> findByIdAndList_Id(Long id, Long listId);
	List<Card> findByList_Id(Long listId);
   
}
