package com.example.demo.domain.post.travelogue.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Cost {

  @Column(nullable = true)
  private Long transportation;

  @Column(nullable = true)
  private Long lodge;

  @Column(nullable = true)
  private Long etc;

  @Column(nullable = false)
  private Long total;

  protected Cost() {
  }

  public Cost(Long transportation, Long lodge, Long etc, Long total) {
    this.transportation = transportation;
    this.lodge = lodge;
    this.etc = etc;
    this.total = total;
  }

  public Long getTransportation() {
    return transportation;
  }

  public Long getLodge() {
    return lodge;
  }

  public Long getEtc() {
    return etc;
  }

  public Long getTotal() {
    return total;
  }
}
