package com.binder.repository;

import com.binder.entity.UserStory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStoryRepository extends CrudRepository<UserStory, Long> {
}
