package com.example.demo.domain.post.travelogue.repository;

import com.example.demo.domain.post.travelogue.entity.Travelogue;
import com.example.demo.domain.post.travelogue.repository.querydsl.TravelogueRepositoryQuerydsl;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelogueRepository extends JpaRepository<Travelogue, Long>,
    TravelogueRepositoryQuerydsl {

  @Query("select t from Travelogue t")
  List<Travelogue> getTravelogues(Pageable pageable);


}
