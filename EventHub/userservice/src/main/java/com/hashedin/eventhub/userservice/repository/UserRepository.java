package com.hashedin.eventhub.userservice.repository;

import com.hashedin.eventhub.userservice.entity.UserDetailsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserDetailsEntity, Long> {

    Optional<UserDetailsEntity> findByEmail(String email);

}
