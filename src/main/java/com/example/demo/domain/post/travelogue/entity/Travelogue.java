package com.example.demo.domain.post.travelogue.entity;

import com.example.demo.domain.base.BaseTimeEntity;
import com.example.demo.domain.post.data.Country;
import com.example.demo.domain.post.subTravelogue.entity.SubTravelogue;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Travelogue extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private Period period;

  @Column(nullable = false)
  private String title;

  @Embedded
  @Column(nullable = false)
  private Country country;

  @Lob
  private String thumbnail;

  @Embedded
  @Column(nullable = false)
  private Cost cost;

  @OneToMany(mappedBy = "travelogue")
  private List<SubTravelogue> subTravelogues = new ArrayList<>();

  public Travelogue(Period period, String title, Country country, String thumbnail, Cost cost) {
    this(period, title, country, thumbnail, cost, new ArrayList<>());
  }

  public Travelogue(Period period, String title, Country country, String thumbnail, Cost cost,
      List<SubTravelogue> subTravelogues) {
    this.period = period;
    this.title = title;
    this.country = country;
    this.thumbnail = thumbnail;
    this.cost = cost;
    this.subTravelogues = subTravelogues;
  }

  protected Travelogue() {
  }

  public Long getId() {
    return id;
  }

  public Period getPeriod() {
    return period;
  }

  public String getTitle() {
    return title;
  }

  public Country getCountry() {
    return country;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public Cost getCost() {
    return cost;
  }

  public List<SubTravelogue> getSubTravelogues() {
    return subTravelogues;
  }

  public void addSubTravelogue(SubTravelogue subTravelogue) {
    this.subTravelogues.add(subTravelogue);
  }
}
