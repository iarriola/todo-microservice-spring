package com.incode.todo.repositories;

import com.incode.todo.repositories.entities.Task;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends ReactiveCrudRepository<Task, UUID> {}
