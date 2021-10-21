package com.hashedin.eventhub.eventservice.repository;

import com.hashedin.eventhub.eventservice.entity.Category;
import com.hashedin.eventhub.eventservice.entity.Event;
import com.hashedin.eventhub.eventservice.entity.UserDetailsEntity;
import com.hashedin.eventhub.eventservice.entity.UserFavoriteEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface FavouriteOperationRepository extends JpaRepository<UserFavoriteEventEntity, String> {

    boolean existsByUserDetailsEntityAndEvent(UserDetailsEntity userDetailsEntity, Event event);

    List<UserFavoriteEventEntity> findByUserDetailsEntityAndStatus(UserDetailsEntity userDetailsEntity, String status);

    Optional<UserFavoriteEventEntity> findByUserDetailsEntityAndEvent(UserDetailsEntity userDetailsEntity, Event event);
}
