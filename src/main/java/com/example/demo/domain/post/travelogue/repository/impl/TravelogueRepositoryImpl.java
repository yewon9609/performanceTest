package com.example.demo.domain.post.travelogue.repository.impl;

import static com.example.demo.domain.post.subTravelogue.entity.QAddress.address;
import static com.example.demo.domain.post.subTravelogue.entity.QSubTravelogue.subTravelogue;
import static com.example.demo.domain.post.travelogue.entity.QTravelogue.travelogue;
import static org.springframework.util.StringUtils.hasText;

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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class TravelogueRepositoryImpl extends QuerydslRepositorySupport implements
    TravelogueRepositoryQuerydsl {

  private static final int SPARE_PAGE = 1;

  private final JPAQueryFactory jpaQueryFactory;

  public TravelogueRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    super(Travelogue.class);
    this.jpaQueryFactory = jpaQueryFactory;
  }

  @Override
  public Slice<TravelogueSimpleRes> search(String keyword, Pageable pageable) {
    List<Long> travelogueIds = getTravelogueIds_contains(keyword, pageable);

    if (travelogueIds.isEmpty()) {
      return new SliceImpl<>(Collections.emptyList());
    }

    List<TravelogueSimpleRes> results = jpaQueryFactory
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
        .orderBy(travelogue.id.desc())
        .limit(pageable.getPageSize() + SPARE_PAGE)
        .offset(pageable.getOffset())
        .fetch()
        .stream()
        .map(TravelogueSimpleRes::toDto)
        .toList();

    return checkLastPage(pageable, results);
  }

  private List<Long> getTravelogueIds_contains(String keyword, Pageable pageable) {

    return jpaQueryFactory.select(travelogue.id)
        .from(travelogue)
        .leftJoin(subTravelogue)
        .on(subTravelogue.travelogue.id.eq(travelogue.id))
        .leftJoin(subTravelogue.addresses, address)
        .where(
            travelogue.title.startsWith(keyword)
                .or(countryContains(keyword))
                .or(subTitleContains(keyword))
                .or(spotContains(keyword))
        )
        .limit(pageable.getPageSize() + SPARE_PAGE)
        .offset(pageable.getOffset())
        .fetch();

  }

  private BooleanExpression titleContains(String keyword) {
    return hasText(keyword) ? travelogue.title.startsWith(keyword) : null;
  }

  private BooleanExpression subTitleContains(String keyword) {
    return hasText(keyword) ? subTravelogue.title.startsWith(keyword) : null;
  }

  private BooleanExpression countryContains(String keyword) {
    return hasText(keyword) ? travelogue.country.name.startsWith(keyword) : null;
  }

  private BooleanExpression contentContains(String keyword) {
    return hasText(keyword) ? subTravelogue.content.startsWith(keyword) : null;
  }

  private BooleanExpression spotContains(String keyword) {
    return hasText(keyword) ?
        address.region.startsWith(keyword) : null;
  }

  private Slice<TravelogueSimpleRes> checkLastPage(Pageable pageable,
      List<TravelogueSimpleRes> results) {

    List<TravelogueSimpleRes> inputResults = new ArrayList<>(results);

    boolean hasNext = false;

    if (results.size() > pageable.getPageSize()) {
      hasNext = true;
      inputResults.remove(pageable.getPageSize());
    }

    return new SliceImpl<>(inputResults, pageable, hasNext);
  }


}

