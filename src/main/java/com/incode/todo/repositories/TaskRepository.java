package com.incode.todo.repositories;

import com.incode.todo.repositories.entities.TaskEntity;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends ReactiveCrudRepository<TaskEntity, UUID> {}
