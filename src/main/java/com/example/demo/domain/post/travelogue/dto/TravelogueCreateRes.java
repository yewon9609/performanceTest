package com.example.demo.domain.post.travelogue.dto;

public record TravelogueCreateRes(
    Long id,
    Long nights,
    Long days
) {
  public static TravelogueCreateRes toDto(Long id, Long nights) {
    return new TravelogueCreateRes(
        id,
        nights,
        nights + 1
    );
  }
}
