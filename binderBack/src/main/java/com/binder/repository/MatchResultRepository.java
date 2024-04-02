package com.binder.repository;

import com.binder.entity.MatchResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchResultRepository extends CrudRepository<MatchResult, Long> {
}
