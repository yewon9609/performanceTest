package com.example.demo.presentation.post.travelogue;

import com.example.demo.domain.post.travelogue.dto.TravelogueCreateReq;
import com.example.demo.domain.post.travelogue.dto.TravelogueCreateRes;
import com.example.demo.domain.post.travelogue.dto.TravelogueSimpleRes;
import com.example.demo.domain.post.travelogue.service.TravelogueService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/travelogues")
public class TravelogueController {

  private final TravelogueService travelogueService;

  public TravelogueController(TravelogueService travelogueService) {
    this.travelogueService = travelogueService;
  }

  private static final int DEFAULT_SIZE = 5;
  private static final String DEFAULT_SORT_FIELD = "createDate";

  @GetMapping
  public ResponseEntity<List<TravelogueSimpleRes>> getAll(
      @PageableDefault(size = DEFAULT_SIZE, sort = DEFAULT_SORT_FIELD, direction = Direction.DESC) Pageable pageable
  ) {
    List<TravelogueSimpleRes> travelogueSimpleRes =
        travelogueService.getTravelogues(pageable);

    return ResponseEntity.ok(travelogueSimpleRes);
  }

  @PostMapping
  public ResponseEntity<TravelogueCreateRes> create(
      @RequestBody @Valid TravelogueCreateReq createReq
  ) {
    TravelogueCreateRes createRes = travelogueService.save(createReq);
    return ResponseEntity.ok(createRes);
  }

  @GetMapping("/search")
  public ResponseEntity<Slice<TravelogueSimpleRes>> search(
      @RequestParam(name = "keyword") String keyword,
      @PageableDefault(size = DEFAULT_SIZE) Pageable pageable
  ) {
    Slice<TravelogueSimpleRes> travelogueSimpleResList =
        travelogueService.search(keyword.trim(), pageable);

    return ResponseEntity.ok(travelogueSimpleResList);
  }


}
