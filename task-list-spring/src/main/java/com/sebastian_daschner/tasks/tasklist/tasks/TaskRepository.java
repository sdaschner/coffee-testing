package com.sebastian_daschner.tasks.tasklist.tasks;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // nothing to do

}
