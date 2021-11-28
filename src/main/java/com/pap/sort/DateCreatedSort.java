package com.pap.sort;

import com.pap.view.task.TaskInstance;

import java.util.Comparator;

/**
 * Sort {@link TaskInstance} by the creation date in increasing fashion or decreasing.
 */
public class DateCreatedSort implements Comparator<TaskInstance> {

    @Override
    public int compare(final TaskInstance task1, final TaskInstance task2)
    {
        if(task1.getTask().getCreationTime().isBefore(task2.getTask().getCreationTime()))
        {
            return 1;
        }
        else if (task1.getTask().getCreationTime().isAfter(task2.getTask().getCreationTime()))
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
