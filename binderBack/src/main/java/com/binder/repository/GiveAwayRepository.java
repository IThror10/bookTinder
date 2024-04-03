package com.binder.repository;

import com.binder.entity.GiveAway;
import com.binder.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GiveAwayRepository extends CrudRepository<GiveAway, Long> {
    List<GiveAway> findAllByUser (User user);

    @Query("SELECT g FROM GiveAway g WHERE g.id NOT IN " +
            "(SELECT m.giveaway.id FROM MatchResult m WHERE m.user.id = :userId) and " +
            "g.book.id NOT IN (SELECT h.book.id FROM UserStory h WHERE h.user.id = :userId)" +
            " ORDER BY id LIMIT 30")
    Set<GiveAway> getAvailableGiveAway(@Param("userId") Long userId);
}
