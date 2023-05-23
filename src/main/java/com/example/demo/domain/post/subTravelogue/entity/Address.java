package com.example.demo.domain.post.subTravelogue.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;

@Embeddable
@Table(name = "travel_address")
public class Address {
  @Column(nullable = false)
  private String region;

  public Address() {
  }

  public Address(String region) {
    this.region = region;
  }

  public String getRegion() {
    return region;
  }

}
