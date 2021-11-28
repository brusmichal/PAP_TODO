package com.pap.view.task.buttons.finish;

import com.pap.database.task.Task;

@FunctionalInterface
public interface Finish {
    void finishTask(final Task task);
}
