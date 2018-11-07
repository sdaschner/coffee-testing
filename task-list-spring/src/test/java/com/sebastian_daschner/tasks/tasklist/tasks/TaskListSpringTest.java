package com.sebastian_daschner.tasks.tasklist.tasks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static com.sebastian_daschner.tasks.tasklist.tasks.TaskTestUtil.createDefaultTask;
import static com.sebastian_daschner.tasks.tasklist.tasks.TaskTestUtil.createTask;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskListSpringTest {

    @Autowired
    private TaskList testObject;

    @MockBean
    private TaskRepository taskRepository;

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