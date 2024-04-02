package com.binder.repository;

import com.binder.entity.GiveAway;
import com.binder.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiveAwayRepository extends CrudRepository<GiveAway, Long> {
    List<GiveAway> findAllByUser (User user);
}
