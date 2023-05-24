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
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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

  private static final int SPARE_PAGE = 1;

  @Override
  public Slice<TravelogueSimpleRes> search(String keyword, Pageable pageable) {

    List<TravelogueSimpleRes> travelogueSimpleRes = jpaQueryFactory.select(travelogue)
        .from(travelogue)
        .join(subTravelogue)
        .on(subTravelogue.travelogue.id.eq(travelogue.id))
        .where(
            travelogue.title.containsIgnoreCase(keyword)
                .or(hasCountry(keyword))
                .or(hasSubContent(keyword))
                .or(hasSubSpot(keyword))
                .or(hasSubTitle(keyword))
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize() + SPARE_PAGE)
        .fetch()
        .stream()
        .map(TravelogueSimpleRes::toDto)
        .collect(Collectors.toList());


    return checkLastPage(pageable, travelogueSimpleRes);
  }

  private BooleanExpression hasTitle(String keyword) {
    return hasText(keyword) ? travelogue.title.containsIgnoreCase(keyword) : null;
  }

  private BooleanExpression hasCountry(String keyword) {
    return hasText(keyword) ? travelogue.country.name.containsIgnoreCase(keyword) : null;
  }

  private BooleanExpression hasSubContent(String keyword) {
    return hasText(keyword) ? subTravelogue.content.containsIgnoreCase(keyword) : null;
  }

  private BooleanExpression hasSubSpot(String keyword) {
    return hasText(keyword) ? subTravelogue.addresses.contains(new Address(keyword)) : null;
  }

  private BooleanExpression hasSubTitle(String keyword) {
    return hasText(keyword) ? subTravelogue.title.contains(keyword) : null;
  }


  private Slice<TravelogueSimpleRes> checkLastPage(Pageable pageable,
      List<TravelogueSimpleRes> results) {

    boolean hasNext = false;

    if (results.size() > pageable.getPageSize()) {
      hasNext = true;
      results.remove(pageable.getPageSize());
    }

    return new SliceImpl<>(results, pageable, hasNext);
  }

}
