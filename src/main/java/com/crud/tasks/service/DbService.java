package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class DbService {
    @Autowired
    private TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task saveTask(final Task task){
        return repository.save(task);
    }

    public Task getTask(final long id) {
        Task task = repository.findById(id);
        return ofNullable(task).orElse(new Task((long) 0,"",""));
       // return repository.findById(id);
    }

    public void delete(final long id) {
        repository.delete(id);
    }
}
