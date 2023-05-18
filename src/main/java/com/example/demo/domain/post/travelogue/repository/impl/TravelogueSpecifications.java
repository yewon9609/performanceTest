package com.example.demo.domain.post.travelogue.repository.impl;

import com.example.demo.domain.post.data.Country;
import com.example.demo.domain.post.subTravelogue.entity.Address;
import com.example.demo.domain.post.subTravelogue.entity.SubTravelogue;
import com.example.demo.domain.post.travelogue.entity.Travelogue;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class TravelogueSpecifications {

  public static Specification<Travelogue> hasTitle(String keyword) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.like(root.<String>get("title"), "%" + keyword + "%");
  }

  public static Specification<Travelogue> hasCountry(String keyword) {
    return (root, query, cb) ->
        cb.equal(root.<String>get("country"), new Country(keyword));
  }

  public static Specification<Travelogue> hasSubTitle(String keyword) {
    return (root, query, criteriaBuilder) -> {
      Join<SubTravelogue, Travelogue> subTravelogues = root.join("subTravelogues");
      return criteriaBuilder.equal(subTravelogues.get("title"), keyword);
    };
  }

  public static Specification<Travelogue> hasSubContent(String keyword) {
    return (root, query, criteriaBuilder) -> {
      Join<SubTravelogue, Travelogue> subTravelogues = root.join("subTravelogues");
      return criteriaBuilder.equal(subTravelogues.get("content"), keyword);
    };
  }

  public static Specification<Travelogue> hasSubSpot(String keyword) {
    return (root, query, criteriaBuilder) -> {
      Join<SubTravelogue, Travelogue> subTravelogues = root.join("subTravelogues");
      Join<Address, SubTravelogue> addresses = subTravelogues.join("addresses");
      return criteriaBuilder.equal(addresses.get("address"), new Address(keyword));
    };
  }

}
