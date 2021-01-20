package com.incode.todo.services;

import com.incode.todo.repositories.TaskRepository;
import com.incode.todo.repositories.entities.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class TaskService {

  private final TaskRepository repository;

  public Flux<Task> getAllTasks() {
    return repository.findAll();
  }
}
