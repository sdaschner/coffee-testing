package com.sebastian_daschner.tasks.tasklist.tasks;

import org.junit.Before;
import org.junit.Test;

import static com.sebastian_daschner.tasks.tasklist.tasks.TaskTestUtil.createDefaultTask;
import static com.sebastian_daschner.tasks.tasklist.tasks.TaskTestUtil.createTask;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TaskListTest {

    private TaskList testObject;
    private TaskRepository taskRepository;

    @Before
    public void setUp() {
        taskRepository = mock(TaskRepository.class);
        testObject = new TaskList(taskRepository);
    }

    @Test
    public void testCreateTask() {
        final Task task = createDefaultTask();
        testObject.createTask(task);

        verify(taskRepository).save(task);
    }

    @Test
    public void testCreateTaskWhitespace() {
        final Task task = createTask("description ");
        testObject.createTask(task);

        assertThat(task.getDescription()).doesNotEndWith(" ");
        verify(taskRepository).save(task);
    }

}