package com.binder.repository;

import com.binder.entity.GiveAway;
import com.binder.entity.MatchResult;
import com.binder.response.MatchResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchResultRepository extends CrudRepository<MatchResult, Long> {
    @Transactional
    @Query("SELECT new com.binder.response.MatchResponse(m2.giveaway, m1.giveaway) " +
        "FROM MatchResult m1 JOIN MatchResult m2 on m1.user.id = :userId " +
        "and m1.user.id = m2.giveaway.user.id and m1.giveaway.user.id = m2.user.id " +
        "and m1.liked and m2.liked")
    List<MatchResponse> getMatches(@Param("userId") Long userId);

    void deleteAllByGiveaway(GiveAway giveAway);
}
