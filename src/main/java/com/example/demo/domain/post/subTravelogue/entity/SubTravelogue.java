package com.example.demo.domain.post.subTravelogue.entity;

import com.example.demo.domain.base.BaseTimeEntity;
import com.example.demo.domain.post.image.TravelPhoto;
import com.example.demo.domain.post.travelogue.entity.Travelogue;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class SubTravelogue extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String title;

  @Column(columnDefinition = "LONGTEXT", nullable = false)
  private String content;

  @Column(name = "day_seq", nullable = false)
  private int day;

  @ElementCollection
  @CollectionTable(name = "travel_address", joinColumns = @JoinColumn(name = "sub_travelogue_id"))
  private List<Address> addresses = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "transportation", joinColumns = @JoinColumn(name = "sub_travelogue_id"))
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Set<Transportation> transportationSet = new HashSet<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "sub_travelogue_id")
  private List<TravelPhoto> photos = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "travelogue_id")
  private Travelogue travelogue;

  public Travelogue getTravelogue() {
    return travelogue;
  }

  public void setTravelogue(Travelogue travelogue) {
    this.travelogue = travelogue;
  }

  protected SubTravelogue() {
  }

  public SubTravelogue(String title, String content, int day, List<Address> addresses,
      Set<Transportation> transportationSet, List<TravelPhoto> photos, Travelogue travelogue) {
    this.title = title;
    this.content = content;
    this.day = day;
    this.addresses = addresses;
    this.transportationSet = transportationSet;
    this.photos = photos;
    this.travelogue = travelogue;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public int getDay() {
    return day;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public Set<Transportation> getTransportationSet() {
    return transportationSet;
  }

  public List<TravelPhoto> getPhotos() {
    return new ArrayList<>(photos);
  }

  public void addPhotos(List<TravelPhoto> travelPhotos) {
    photos.addAll(travelPhotos);
  }
}
