package com.hashedin.eventhub.eventservice.repository;

import com.hashedin.eventhub.eventservice.entity.Category;
import com.hashedin.eventhub.eventservice.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@EnableJpaRepositories
public interface EventRepository extends JpaRepository<Event,Long> {

    @Query("Select e from Event e order by e.eventDate")
    List<Event> orderDate();

    @Query("Select e from Event e where e.category.categoryName=:category")
    List<Event> findByCategory(String category);

    @Query("Select e from Event e where e.owner.email=:userId")
    List<Event> getAllEventsCreatedByUser(String userId);
}
