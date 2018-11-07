package com.sebastian_daschner.tasks.tasklist.tasks;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class TaskRestController {

    private final TaskList taskList;

    public TaskRestController(TaskList taskList) {
        this.taskList = taskList;
    }

    @GetMapping("/tasks")
    public Collection<Task> getAllTasks() {
        return taskList.getAllTasks();
    }

    @PostMapping(value = "/tasks", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addTask(@Validated @RequestBody Task task) {
        taskList.createTask(task);
    }

}
