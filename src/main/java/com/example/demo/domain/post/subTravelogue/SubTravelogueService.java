package com.example.demo.domain.post.subTravelogue;

import com.example.demo.domain.post.image.dto.TravelPhotoCreateReq;
import com.example.demo.domain.post.subTravelogue.dto.SubTravelogueCreateReq;
import com.example.demo.domain.post.subTravelogue.dto.SubTravelogueCreateRes;
import com.example.demo.domain.post.subTravelogue.entity.SubTravelogue;
import com.example.demo.domain.post.subTravelogue.repository.SubTravelogueRepository;
import com.example.demo.domain.post.travelogue.entity.Travelogue;
import com.example.demo.domain.post.travelogue.service.TravelogueService;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SubTravelogueService {

  private final SubTravelogueRepository subTravelogueRepository;
  private final TravelogueService travelogueService;

  public SubTravelogueService(SubTravelogueRepository subTravelogueRepository,
      TravelogueService travelogueService) {
    this.subTravelogueRepository = subTravelogueRepository;
    this.travelogueService = travelogueService;
  }

  @Transactional
  public SubTravelogueCreateRes save(SubTravelogueCreateReq createReq, Long travelogueId) {
    Travelogue travelogue = travelogueService.getTravelogue(travelogueId);
    SubTravelogue subTravelogue = subTravelogueRepository.save(createReq.toSubTravelogue(travelogue));
    addPhotosTo(subTravelogue, createReq);
    travelogue.addSubTravelogue(subTravelogue);
    return new SubTravelogueCreateRes(subTravelogue.getId());
  }

  private void addPhotosTo(
      SubTravelogue subTravelogue,
      SubTravelogueCreateReq subTravelogueCreateReq
  ) {
    if (subTravelogue.getPhotos().isEmpty()) {
      return;
    }
    subTravelogue.getPhotos()
        .addAll(subTravelogueCreateReq.travelPhotoCreateReqs()
            .stream()
            .map(TravelPhotoCreateReq::toEntity)
            .toList());
  }
}
