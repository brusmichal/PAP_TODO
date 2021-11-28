package com.pap.view.task.buttons.remove;

import com.pap.database.task.Task;

@FunctionalInterface
public interface Remove {
    void removeTask(final Task task);
}
