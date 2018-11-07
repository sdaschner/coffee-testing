package com.sebastian_daschner.tasks.tasklist.tasks;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static com.sebastian_daschner.tasks.tasklist.tasks.TaskTestUtil.createDefaultTask;


@DataJpaTest
@RunWith(SpringRunner.class)
public class TaskJpaTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void create() {
        Task task = taskRepository.saveAndFlush(createDefaultTask());
        Assertions.assertThat(task.getId()).isNotNull();
        Assertions.assertThat(task.getDescription()).isEqualTo("description");
        TaskAssert.assertThat(task).isCreated();
        Assertions.assertThat(task.getAssigned()).isNull();
    }

    @MockBean
    private RestTemplate restTemplate;

}
