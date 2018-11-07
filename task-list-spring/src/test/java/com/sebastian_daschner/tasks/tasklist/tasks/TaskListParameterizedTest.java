package com.sebastian_daschner.tasks.tasklist.tasks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(Parameterized.class)
public class TaskListParameterizedTest {

    @Parameterized.Parameter
    public String initialDescription;

    @Parameterized.Parameter(1)
    public String expectedDescription;

    private TaskList testObject;
    private TaskRepository taskRepository;

    @Before
    public void setUp() {
        taskRepository = mock(TaskRepository.class);
        testObject = new TaskList(taskRepository);
    }

    @Test
    public void testCreateTask() {
        final Task task = TaskTestUtil.createTask(initialDescription);
        testObject.createTask(task);

        assertThat(task.getDescription()).isEqualTo(expectedDescription);
        verify(taskRepository).save(task);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(
                new String[]{"description", "description"},
                new String[]{"description ", "description"},
                new String[]{" description", "description"},
                new String[]{" description ", "description"});
    }

}