package com.binder.repository;

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
    @Query("SELECT new com.binder.response.MatchResponse(m1.giveaway, m2.giveaway) " +
        "FROM MatchResult m1 JOIN MatchResult m2 on m1.user.id = :userId " +
        "and m1.user.id = m2.giveaway.user.id")
    List<MatchResponse> getMatches(@Param("userId") Long userId);
}
