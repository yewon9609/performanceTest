package com.example.demo.presentation.post.subTravelogue;

import com.example.demo.domain.post.subTravelogue.SubTravelogueService;
import com.example.demo.domain.post.subTravelogue.dto.SubTravelogueCreateReq;
import com.example.demo.domain.post.subTravelogue.dto.SubTravelogueCreateRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/travelogues")
public class SubTravelogueController {
  private final SubTravelogueService subTravelogueService;

  public SubTravelogueController(SubTravelogueService subTravelogueService) {
    this.subTravelogueService = subTravelogueService;
  }

  @PostMapping("/{travelogueId}/subTravelogues")
  public ResponseEntity<SubTravelogueCreateRes> create(
      @RequestBody SubTravelogueCreateReq createReq,
      @PathVariable Long travelogueId) {

    SubTravelogueCreateRes subTravelogueCreateRes = subTravelogueService.save(createReq,
        travelogueId);
    return ResponseEntity.ok(subTravelogueCreateRes);
  }
}
