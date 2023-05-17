package com.example.demo.domain.post.image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TravelPhoto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String url;

  protected TravelPhoto() {
  }

  public TravelPhoto(String url) {
    this.url = url;
  }

  public Long getId() {
    return id;
  }

  public String getUrl() {
    return url;
  }
}
