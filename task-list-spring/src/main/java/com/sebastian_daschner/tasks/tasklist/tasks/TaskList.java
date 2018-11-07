package com.sebastian_daschner.tasks.tasklist.tasks;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class TaskList {

    private final TaskRepository taskRepository;

    public TaskList(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public void createTask(Task task) {
        task.setDescription(task.getDescription().trim());
        taskRepository.save(task);
    }

    public Collection<Task> getAllTasks() {
        return taskRepository.findAll(Sort.by(Sort.Order.asc("created")));
    }

}
