package com.example.demo.domain.post.travelogue.service;

import com.example.demo.domain.post.travelogue.dto.TravelogueCreateReq;
import com.example.demo.domain.post.travelogue.dto.TravelogueCreateRes;
import com.example.demo.domain.post.travelogue.dto.TravelogueSimpleRes;
import com.example.demo.domain.post.travelogue.entity.Travelogue;
import com.example.demo.domain.post.travelogue.repository.TravelogueRepository;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TravelogueService {

  private final TravelogueRepository travelogueRepository;

  public TravelogueService(TravelogueRepository travelogueRepository) {
    this.travelogueRepository = travelogueRepository;
  }

  @Transactional
  public TravelogueCreateRes save(TravelogueCreateReq createReq) {
    Travelogue travelogue = travelogueRepository.save(createReq.toTravelogue());
    Long nights = travelogue.getPeriod().getNights();
    return TravelogueCreateRes.toDto(travelogue.getId(), nights);
  }

  public Travelogue getTravelogue(Long travelogueId) {
    return travelogueRepository.findById(travelogueId)
        .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시글이 없습니다"));
  }

  public List<TravelogueSimpleRes> getTravelogues(Pageable pageable) {
    return travelogueRepository.getTravelogues(pageable)
        .stream()
        .map(TravelogueSimpleRes::toDto)
        .toList();
  }

  public Slice<TravelogueSimpleRes> search(String keyword, Pageable pageable) {
    return travelogueRepository.search(keyword, pageable);

  }

}
