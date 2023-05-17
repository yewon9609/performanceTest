package com.example.demo.domain.post.subTravelogue.repository;

import com.example.demo.domain.post.subTravelogue.entity.SubTravelogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTravelogueRepository extends JpaRepository<SubTravelogue, Long> {

}
