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

import java.time.LocalDateTime;
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
        generateData();
    }

    // TODO REMOVE
    // Mock data
    public void generateData()
    {
        final var task1 = Task.builder()
                .withId("1")
                .withTitle("O")
                .withCreationTime(LocalDateTime.now().plusDays(3))
                .withDueDate(LocalDateTime.now().plusDays(1))
                .withEndTime(LocalDateTime.now())
                .withStatus(Status.TO_DO)
                .build();

        final var task2 = Task.builder()
                .withId("2")
                .withTitle("C")
                .withCreationTime(LocalDateTime.now().plusDays(20))
                .withDueDate(LocalDateTime.now().plusDays(1))
                .withEndTime(LocalDateTime.now().plusDays(4))
                .withStatus(Status.TO_DO)
                .build();

        final var task3 = Task.builder()
                .withId("3")
                .withTitle("D")
                .withCreationTime(LocalDateTime.now())
                .withDueDate(LocalDateTime.now().plusDays(2))
                .withEndTime(LocalDateTime.now().plusDays(1))
                .withStatus(Status.TO_DO)
                .build();

        final var task4 = Task.builder()
                .withId("4")
                .withTitle("B")
                .withCreationTime(LocalDateTime.now().plusDays(10))
                .withDueDate(LocalDateTime.now().minusDays(1))
                .withEndTime(LocalDateTime.now().plusDays(5))
                .withStatus(Status.TO_DO)
                .build();

        final var task5 = Task.builder()
                .withId("5")
                .withTitle("A")
                .withCreationTime(LocalDateTime.now())
                .withDueDate(LocalDateTime.now().plusDays(2))
                .withEndTime(LocalDateTime.now().plusDays(1))
                .withStatus(Status.TO_DO)
                .build();

        addTask(task1);
        addTask(task2);
        addTask(task3);
        addTask(task4);
        addTask(task5);

    }


    public void addTask(final Task task)
    {
        // taskRepository.insert(task);
        final var taskInstance = TaskInstance.ofDoneTask(task,this);
        gridPane.add(taskInstance,0,tasks.size());
        tasks.add(taskInstance);

    }

    @Override
    public void removeTask(final Task task)
    {
        // taskRepository.insert(task);
        task.setStatus(Status.TRASHED);
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
        //gridPane.getChildren().addAll(tasks);
    }

}
