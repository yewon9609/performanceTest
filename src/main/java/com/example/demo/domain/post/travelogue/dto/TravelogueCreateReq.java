package com.example.demo.domain.post.travelogue.dto;

import com.example.demo.domain.post.data.Country;
import com.example.demo.domain.post.travelogue.entity.Cost;
import com.example.demo.domain.post.travelogue.entity.Period;
import com.example.demo.domain.post.travelogue.entity.Travelogue;
import jakarta.validation.constraints.NotNull;

public record TravelogueCreateReq (
  @NotNull
  Period period,
  @NotNull
  String title,
  @NotNull
  Country country,
  @NotNull
  String thumbnail,
  @NotNull
  Cost cost
) {
  public Travelogue toTravelogue() {
    return new Travelogue(
        period,
        title,
        country,
        thumbnail,
        cost
    );
  }
}
