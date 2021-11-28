package com.pap.view.task.buttons.update;

import com.pap.database.task.Task;

@FunctionalInterface
public interface Update {
    void updateTask(final Task task);
}
