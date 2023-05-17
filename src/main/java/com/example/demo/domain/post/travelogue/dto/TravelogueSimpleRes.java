package com.example.demo.domain.post.travelogue.dto;

import com.example.demo.domain.post.travelogue.entity.Travelogue;

public record TravelogueSimpleRes (
  Long travelogueId,
  String title,
  Long nights,
  Long days,
  Long totalCost,
  String country,
  String thumbnail
) {
  public static TravelogueSimpleRes toDto(
      Travelogue travelogue
  ) {
    long nights = travelogue.getPeriod().getNights();

    return new TravelogueSimpleRes(
        travelogue.getId(),
        travelogue.getTitle(),
        nights,
        nights + 1,
        travelogue.getCost().getTotal(),
        travelogue.getCountry().getName(),
        travelogue.getThumbnail()
    );
  }

  public static TravelogueSimpleRes toDto(
      TravelogueSimple travelogueSimple
  ) {
    long nights = travelogueSimple.period().getNights();

    return new TravelogueSimpleRes(
        travelogueSimple.travelogueId(),
        travelogueSimple.title(),
        nights,
        nights + 1,
        travelogueSimple.totalCost(),
        travelogueSimple.country(),
        travelogueSimple.thumbnail()
    );
  }
}
