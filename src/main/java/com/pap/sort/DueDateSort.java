package com.pap.sort;

import com.pap.view.task.TaskInstance;

import java.util.Comparator;

/**
 * Sort {@link TaskInstance} by the due date in increasing fashion or decreasing.
 */
public class DueDateSort implements Comparator<TaskInstance> {
    @Override
    public int compare(final TaskInstance task1, final TaskInstance task2)
    {
        if(task1.getTask().getDueDate().isBefore(task2.getTask().getDueDate()))
        {
            return 1;
        }
        else if (task1.getTask().getDueDate().isAfter(task2.getTask().getDueDate()))
        {
            return -1;
        }
        return 0;
    }

    @Override
    public Comparator<TaskInstance> reversed()
    {
        return Comparator.super.reversed();
    }
}
