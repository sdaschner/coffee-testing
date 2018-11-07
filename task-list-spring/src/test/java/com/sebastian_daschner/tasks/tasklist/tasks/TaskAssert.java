package com.sebastian_daschner.tasks.tasklist.tasks;

import org.assertj.core.api.AbstractAssert;

public class TaskAssert extends AbstractAssert<TaskAssert, Task> {

    public TaskAssert(Task task) {
        super(task, TaskAssert.class);
    }

    public static TaskAssert assertThat(Task actual) {
        return new TaskAssert(actual);
    }

    public TaskAssert isCreated() {
        isNotNull();
        if (actual.getCreated() == null) {
            failWithMessage("Expected the task to be have created date but was null");
        }
        return this;
    }

    public TaskAssert isAssigned() {
        isNotNull();
        if (actual.getAssigned() == null) {
            failWithMessage("Expected the task to be have assigned date but was null");
        }
        return this;
    }
}
