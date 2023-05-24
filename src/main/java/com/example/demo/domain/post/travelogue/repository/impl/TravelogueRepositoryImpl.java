package com.example.demo.domain.post.travelogue.repository.impl;

import static com.example.demo.domain.post.subTravelogue.entity.QAddress.address;
import static com.example.demo.domain.post.subTravelogue.entity.QSubTravelogue.subTravelogue;
import static com.example.demo.domain.post.travelogue.entity.QTravelogue.travelogue;
import static org.springframework.util.StringUtils.hasText;

import com.example.demo.domain.post.subTravelogue.entity.Address;
import com.example.demo.domain.post.travelogue.dto.TravelogueSimple;
import com.example.demo.domain.post.travelogue.dto.TravelogueSimpleRes;
import com.example.demo.domain.post.travelogue.entity.Travelogue;
import com.example.demo.domain.post.travelogue.repository.querydsl.TravelogueRepositoryQuerydsl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class TravelogueRepositoryImpl extends QuerydslRepositorySupport implements
    TravelogueRepositoryQuerydsl {

  private static final int SPARE_PAGE = 1;
  private static final int SPARE_DAY = 1;

  private final JPAQueryFactory jpaQueryFactory;

  public TravelogueRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    super(Travelogue.class);
    this.jpaQueryFactory = jpaQueryFactory;
  }

  @Override
  public Slice<TravelogueSimpleRes> search(String keyword, Pageable pageable) {
    List<Long> subTravelogueIds = getSubTravelogueIds(keyword);
    List<Long> travelogueIds = getTravelogueIds(keyword, subTravelogueIds);

    if (travelogueIds.isEmpty()) {
      return new SliceImpl<>(Collections.emptyList());
    }

    List<TravelogueSimple> travelogueSimpleList = jpaQueryFactory
        .select(
            Projections.constructor(
                TravelogueSimple.class,
                travelogue.id,
                travelogue.title,
                travelogue.period,
                travelogue.cost.total,
                travelogue.country.name,
                travelogue.thumbnail
            )
        )
        .from(travelogue)
        .where(travelogue.id.in(travelogueIds))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize() + SPARE_PAGE)
        .orderBy(travelogue.id.desc())
        .groupBy(travelogue.id)
        .fetch();

    List<TravelogueSimple> results = new ArrayList<>(travelogueSimpleList);

    return checkLastPage(pageable, results);
  }

  private List<Long> getTravelogueIds(String keyword, List<Long> subTravelogueIds) {
    return Stream.concat(getTravelogueIds_containsSubTravelogues(subTravelogueIds).stream(),
            getTravelogueIds_contains(keyword).stream())
        .distinct()
        .toList();
  }

  private List<Long> getTravelogueIds_containsSubTravelogues(
      List<Long> subTravelogueIds
  ) {
    return jpaQueryFactory
        .select(travelogue.id)
        .from(travelogue)
        .leftJoin(travelogue.subTravelogues, subTravelogue)
        .where(
            subTravelogue.id.in(subTravelogueIds)
        )
        .fetch();
  }

  private List<Long> getTravelogueIds_contains(String keyword) {
    return jpaQueryFactory
        .select(travelogue.id)
        .from(travelogue)
        .where(
            titleContains(keyword)
                .or(countryContains(keyword))
        )
        .distinct()
        .orderBy(travelogue.id.desc())
        .fetch();
  }


  private List<Long> getSubTravelogueIds(String keyword) {
    return jpaQueryFactory
        .select(subTravelogue.id)
        .from(subTravelogue)
        .leftJoin(subTravelogue.addresses, address)
        .on(subTravelogue.addresses.contains(address))
        .where(
            subTitleContains(keyword)
                .or(contentContains(keyword))
                .or(spotContains(keyword))
        )
        .distinct()
        .orderBy(subTravelogue.id.desc())
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

  private Slice<TravelogueSimpleRes> checkLastPage(Pageable pageable,
      List<TravelogueSimple> results) {

    boolean hasNext = false;

    if (results.size() > pageable.getPageSize()) {
      hasNext = true;
      results.remove(pageable.getPageSize());
    }

    return new SliceImpl<>(results.stream()
        .map(TravelogueSimpleRes::toDto)
        .toList(), pageable, hasNext);
  }

}
