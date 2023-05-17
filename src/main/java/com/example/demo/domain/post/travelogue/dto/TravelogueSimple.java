package com.example.demo.domain.post.travelogue.dto;

import com.example.demo.domain.post.travelogue.entity.Period;

public record TravelogueSimple(
    Long travelogueId,
    String title,
    Period period,
    Long totalCost,
    String country,
    String thumbnail
) {


}
