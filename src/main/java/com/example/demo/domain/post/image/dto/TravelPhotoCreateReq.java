package com.example.demo.domain.post.image.dto;

import com.example.demo.domain.post.image.TravelPhoto;

public record TravelPhotoCreateReq(
    String url
) {

  public TravelPhoto toEntity() {
    return new TravelPhoto(
        url
    );
  }

  public static TravelPhotoCreateReq toDto(TravelPhoto travelPhoto) {
    return new TravelPhotoCreateReq(
        travelPhoto.getUrl()
    );
  }
}
