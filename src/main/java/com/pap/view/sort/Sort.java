package com.pap.view.sort;

import com.pap.view.task.TaskInstance;

import java.util.Comparator;

public interface Sort {
    void sortBy(Comparator<TaskInstance> comparator);
}
