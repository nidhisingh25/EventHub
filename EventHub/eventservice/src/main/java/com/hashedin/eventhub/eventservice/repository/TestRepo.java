package com.hashedin.eventhub.eventservice.repository;

import com.hashedin.eventhub.eventservice.entity.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface TestRepo extends CrudRepository<TestEntity, Long> {
}
