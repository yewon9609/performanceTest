package com.example.demo.domain.post.travelogue.repository.querydsl;

import com.example.demo.domain.post.travelogue.dto.TravelogueSimpleRes;
import java.util.List;

public interface TravelogueRepositoryQuerydsl {

  List<TravelogueSimpleRes> search(String keyword);


}
