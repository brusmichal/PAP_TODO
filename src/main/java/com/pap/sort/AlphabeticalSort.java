package com.pap.sort;

import com.pap.view.task.TaskInstance;

import java.util.Comparator;

/**
 * Sort {@link TaskInstance} alphabetically.
 */
public class AlphabeticalSort implements Comparator<TaskInstance> {
    @Override
    public int compare(final TaskInstance o1, final TaskInstance o2)
    {
        return o1.getTask().getTitle().compareTo(o2.getTask().getTitle());
    }

    @Override
    public Comparator<TaskInstance> reversed()
    {
        return Comparator.super.reversed();
    }
}
