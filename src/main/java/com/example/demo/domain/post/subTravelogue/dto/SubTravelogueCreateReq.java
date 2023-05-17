package com.example.demo.domain.post.subTravelogue.dto;

import com.example.demo.domain.post.image.TravelPhoto;
import com.example.demo.domain.post.image.dto.TravelPhotoCreateReq;
import com.example.demo.domain.post.subTravelogue.entity.Address;
import com.example.demo.domain.post.subTravelogue.entity.SubTravelogue;
import com.example.demo.domain.post.subTravelogue.entity.Transportation;
import com.example.demo.domain.post.travelogue.entity.Travelogue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public record SubTravelogueCreateReq(
    String title,
    String content,
    int day,
    List<Address> addresses,
    Set<Transportation> transportationSet,
    List<TravelPhotoCreateReq> travelPhotoCreateReqs
) {

  public SubTravelogue toSubTravelogue(Travelogue travelogue) {
    return new SubTravelogue(
        title,
        content,
        day,
        toAddresses(),
        transportationSet,
        toTravelPhotos(),
        travelogue
    );
  }

  private List<Address> toAddresses() {
    if (Objects.isNull(addresses)) {
      return new ArrayList<>();
    }

    return addresses;
  }

  private List<TravelPhoto> toTravelPhotos() {
    if (Objects.isNull(travelPhotoCreateReqs)) {
      return new ArrayList<>();
    }

    return travelPhotoCreateReqs.stream()
        .map(TravelPhotoCreateReq::toEntity)
        .toList();
  }
}
