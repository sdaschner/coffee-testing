package com.sebastian_daschner.tasks.tasklist.tasks;

public final class TaskTestUtil {

    private TaskTestUtil() {
    }

    public static Task createDefaultTask() {
        return createTask("description");
    }

    public static Task createTask(String description) {
        final Task task = new Task();
        task.setDescription(description);
        return task;
    }

}
