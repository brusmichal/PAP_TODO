package com.pap.view.task.buttons.add;

import com.pap.database.task.Task;

@FunctionalInterface
public interface Add {
    void addTask(final Task task);
}
