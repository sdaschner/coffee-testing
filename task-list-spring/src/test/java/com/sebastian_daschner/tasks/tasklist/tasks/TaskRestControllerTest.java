package com.sebastian_daschner.tasks.tasklist.tasks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TaskRestController.class)
@RunWith(SpringRunner.class)
public class TaskRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TaskList taskList;

    @Test
    public void getAllTasks() throws Exception {

        Mockito.when(taskList.getAllTasks())
                .thenReturn(Collections.singletonList(TaskTestUtil.createDefaultTask()));

        mvc.perform(MockMvcRequestBuilders.get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("@.[0].description").value("description"));
    }

}
