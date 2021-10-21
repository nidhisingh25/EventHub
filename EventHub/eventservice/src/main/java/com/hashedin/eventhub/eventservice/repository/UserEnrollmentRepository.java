package com.hashedin.eventhub.eventservice.repository;

import com.hashedin.eventhub.eventservice.entity.Event;
import com.hashedin.eventhub.eventservice.entity.UserDetailsEntity;
import com.hashedin.eventhub.eventservice.entity.UserEnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserEnrollmentRepository extends JpaRepository<UserEnrollmentEntity, Long> {

    List<UserEnrollmentEntity> findByEvent(Event event);

    @Query("SELECT COUNT(*) FROM UserEnrollmentEntity enroll WHERE enroll.event.eventId = :eventId")
    int getCountOfParticipants(Long eventId);

    @Query("SELECT enroll FROM UserEnrollmentEntity enroll WHERE enroll.userDetailsEntity.id =?1")
    List<UserEnrollmentEntity> getEnrolledEventForUser(Long userId);

    boolean existsByUserDetailsEntityAndEvent(UserDetailsEntity userDetailsEntity, Event event);

    List<UserEnrollmentEntity> findByUserDetailsEntity(UserDetailsEntity userDetailsEntity);

    List<UserEnrollmentEntity> findByUserDetailsEntityAndStatus(UserDetailsEntity userDetailsEntity, String status);

    Optional<UserEnrollmentEntity> findByUserDetailsEntityAndEvent(UserDetailsEntity userDetailsEntity, Event event);
}
