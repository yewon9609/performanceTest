package com.example.demo.domain.post.travelogue.repository.impl;

import static com.example.demo.domain.post.subTravelogue.entity.QSubTravelogue.subTravelogue;
import static com.example.demo.domain.post.travelogue.entity.QTravelogue.travelogue;
import static org.springframework.util.StringUtils.hasText;

import com.example.demo.domain.post.subTravelogue.entity.Address;
import com.example.demo.domain.post.travelogue.dto.TravelogueSimpleRes;
import com.example.demo.domain.post.travelogue.entity.Travelogue;
import com.example.demo.domain.post.travelogue.repository.querydsl.TravelogueRepositoryQuerydsl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class TravelogueRepositoryImpl extends QuerydslRepositorySupport implements
    TravelogueRepositoryQuerydsl {

  private final JPAQueryFactory jpaQueryFactory;

  public TravelogueRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    super(Travelogue.class);
    this.jpaQueryFactory = jpaQueryFactory;
  }

  @Override
  public List<TravelogueSimpleRes> search(String keyword) {
    List<List<Travelogue>> searchRequirements = List.of(hasTitle(keyword), hasCountry(keyword),
        hasSubTitle(keyword), hasSubContent(keyword), hasSubSpot(keyword));
    List<Travelogue> searchResults = new ArrayList<>();

    for (List<Travelogue> requirement : searchRequirements) {
      searchResults = getTravelogues(requirement, searchResults);
    }

    return searchResults.stream()
        .map(TravelogueSimpleRes::toDto)
        .toList();
  }

  private List<Travelogue> getTravelogues(List<Travelogue> foundList, List<Travelogue> result) {
    return Stream.concat(foundList.stream(), result.stream())
        .distinct()
        .toList();
  }

  private List<Travelogue> hasTitle(String keyword) {
    return jpaQueryFactory
        .select(travelogue)
        .from(travelogue)
        .where(titleContains(keyword))
        .fetch();
  }

  private List<Travelogue> hasCountry(String keyword) {
    return jpaQueryFactory
        .select(travelogue)
        .from(travelogue)
        .where(countryContains(keyword))
        .fetch();
  }

  private List<Travelogue> hasSubContent(String keyword) {
    return jpaQueryFactory
        .select(travelogue)
        .from(travelogue)
        .leftJoin(subTravelogue)
        .on(subTravelogue.travelogue.id.eq(travelogue.id))
        .where(
            contentContains(keyword)
        )
        .fetch();
  }

  private List<Travelogue> hasSubSpot(String keyword) {
    return jpaQueryFactory
        .select(travelogue)
        .from(travelogue)
        .leftJoin(subTravelogue)
        .on(subTravelogue.travelogue.id.eq(travelogue.id))
        .where(
            spotContains(keyword)
        )
        .fetch();
  }

  private List<Travelogue> hasSubTitle(String keyword) {
    return jpaQueryFactory
        .select(travelogue)
        .from(travelogue)
        .leftJoin(subTravelogue)
        .on(subTravelogue.travelogue.id.eq(travelogue.id))
        .where(
            subTitleContains(keyword)
        )
        .fetch();
  }

  private BooleanExpression titleContains(String keyword) {
    return hasText(keyword) ? travelogue.title.contains(keyword) : null;
  }

  private BooleanExpression subTitleContains(String keyword) {
    return hasText(keyword) ? subTravelogue.title.contains(keyword) : null;
  }

  private BooleanExpression countryContains(String keyword) {
    return hasText(keyword) ? travelogue.country.name.contains(keyword) : null;
  }

  private BooleanExpression contentContains(String keyword) {
    return hasText(keyword) ? subTravelogue.content.contains(keyword) : null;
  }

  private BooleanExpression spotContains(String keyword) {
    return hasText(keyword) ?
        subTravelogue.addresses.contains(new Address(keyword)) : null;
  }


}
