package com.binder.repository;

import com.binder.entity.GiveAway;
import com.binder.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GiveAwayRepository extends CrudRepository<GiveAway, Long> {
    @Transactional
    List<GiveAway> findAllByUser (User user);

    @Transactional
    @Query("SELECT g FROM GiveAway g WHERE g.user.id != :userId and g.id NOT IN " +
            "(SELECT m.giveaway.id FROM MatchResult m WHERE m.user.id = :userId) and " +
            "g.book.id NOT IN (SELECT h.book.id FROM UserStory h WHERE h.user.id = :userId)" +
            " ORDER BY id DESC LIMIT 10")
    Set<GiveAway> getAvailableUnreadGiveAway(@Param("userId") Long userId);

    @Transactional
    @Query("SELECT g FROM GiveAway g WHERE g.user.id != :userId and g.id NOT IN " +
            "(SELECT m.giveaway.id FROM MatchResult m WHERE m.user.id = :userId) and " +
            "g.book.id NOT IN (SELECT h.book.id FROM UserStory h WHERE h.user.id = :userId) " +
            "and g.user.id IN (SELECT m.user.id FROM MatchResult m WHERE m.giveaway.user.id = :userId) " +
            "ORDER BY id DESC LIMIT 2")
    Set<GiveAway> getMatchedUs(@Param("userId") Long userId);

    @Transactional
    @Query("SELECT g FROM GiveAway g WHERE g.user.id != :userId and g.id NOT IN " +
            "(SELECT m.giveaway.id FROM MatchResult m WHERE m.user.id = :userId) and " +
            "g.book.id NOT IN (SELECT h.book.id FROM UserStory h WHERE h.user.id = :userId) " +
            "and g.user.id IN (SELECT m.user.id FROM MatchResult m WHERE m.giveaway.user.id = :userId) " +
            "ORDER BY id DESC LIMIT 3")
    Set<GiveAway> getRecommendPrediction(@Param("userId") Long userId);
}
