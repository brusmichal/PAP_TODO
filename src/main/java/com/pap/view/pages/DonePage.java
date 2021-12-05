package com.pap.view.pages;

import com.pap.database.Status;
import com.pap.database.task.Task;
import com.pap.database.task.repository.TaskRepository;
import com.pap.sort.SortingProperties;
import com.pap.view.sort.Sort;
import com.pap.view.sort.SortingPanel;
import com.pap.view.task.TaskInstance;
import com.pap.view.task.buttons.remove.Remove;
import lombok.Getter;

import java.util.Comparator;

@Getter
public class DonePage extends DefaultPage<SortingPanel> implements Remove, Sort {
    private final TaskRepository taskRepository;

    public DonePage(final TaskRepository taskRepository,final SortingProperties sortingProperties)
    {
        super();
        this.taskRepository = taskRepository;
        this.lowerPanel = new SortingPanel(sortingProperties,this);

        setUpVBox();
        taskRepository.getTasksByUserAndStatus("root", Status.DONE)
                .forEach(this::addTask);

    }


    public void addTask(final Task task)
    {
        final var taskInstance = TaskInstance.ofDoneTask(task,this);
        gridPane.add(taskInstance,0,tasks.size());
        tasks.add(taskInstance);

    }

    @Override
    public void removeTask(final Task task)
    {

        task.setStatus(Status.TRASHED);
        taskRepository.save(task);
        getInstanceFromChildren(task).ifPresent(instance -> {
            tasks.remove(instance);
            addTasksToGridPane();
        });
    }

    @Override
    public void sortBy(final Comparator<TaskInstance> comparator)
    {
        tasks.sort(comparator);
        addTasksToGridPane();
    }

}
