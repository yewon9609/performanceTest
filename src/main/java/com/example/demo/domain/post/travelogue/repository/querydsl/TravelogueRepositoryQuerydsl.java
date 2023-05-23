package com.example.demo.domain.post.travelogue.repository.querydsl;

import com.example.demo.domain.post.travelogue.dto.TravelogueSimpleRes;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface TravelogueRepositoryQuerydsl {

  Slice<TravelogueSimpleRes> search(String keyword, Pageable pageable);


}
