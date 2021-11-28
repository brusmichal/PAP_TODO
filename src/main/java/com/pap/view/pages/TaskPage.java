package com.pap.view.pages;

import com.pap.database.Status;
import com.pap.database.task.Task;
import com.pap.database.task.repository.TaskRepository;
import com.pap.view.task.NewTask;
import com.pap.view.task.TaskInstance;
import com.pap.view.task.buttons.TaskEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskPage extends DefaultPage<NewTask> implements TaskEvents {
    private TaskRepository taskRepository;

    @Autowired
    public TaskPage(final TaskRepository taskRepository)
    {
        super();
        this.taskRepository = taskRepository;
        this.lowerPanel = new NewTask(this);
        setUpVBox();
        generateData();
    }

    // Mock data
    public void generateData()
    {
        final var task1 = Task.builder()
                .withId("1")
                .withTitle("TITLE ONE")
                .withCreationTime(LocalDateTime.now())
                .withDueDate(LocalDateTime.now().plusDays(1))
                .withStatus(Status.TO_DO)
                .build();

        final var task2 = Task.builder()
                .withId("2")
                .withTitle("TITLE TW")
                .withCreationTime(LocalDateTime.now())
                .withDueDate(LocalDateTime.now().plusDays(1))
                .withStatus(Status.TO_DO)
                .build();

        final var task3 = Task.builder()
                .withId("3")
                .withTitle("TITLE TREE")
                .withCreationTime(LocalDateTime.now())
                .withDueDate(LocalDateTime.now().plusDays(1))
                .withStatus(Status.TO_DO)
                .build();

        final var task4 = Task.builder()
                .withId("4")
                .withTitle("TITLE FOR")
                .withCreationTime(LocalDateTime.now())
                .withDueDate(LocalDateTime.now().minusDays(1))
                .withStatus(Status.TO_DO)
                .build();

        final var task5 = Task.builder()
                .withId("5")
                .withTitle("TITLE ONE")
                .withCreationTime(LocalDateTime.now())
                .withDueDate(LocalDateTime.now().plusDays(2))
                .withStatus(Status.TO_DO)
                .build();

        addTask(task1);
        addTask(task2);
        addTask(task3);
        addTask(task4);
        addTask(task5);

    }

    @Override
    public void addTask(final Task task)
    {
        // taskRepository.insert(task);
        final var taskInstance = TaskInstance.fromTask(task,this);
        gridPane.add(taskInstance,0,tasks.size());
        tasks.add(taskInstance);

    }

    @Override
    public void finishTask(final Task task)
    {
        task.setEndTime(LocalDateTime.now());
        task.setStatus(Status.DONE);
        getInstanceFromChildren(task).ifPresent(instance -> {
            tasks.remove(instance);
            addTasksToGridPane();
        });
        // taskRepository.insert(task);
    }

    @Override
    public void removeTask(final Task task)
    {
        //taskRepository.insert(task);
        task.setStatus(Status.DELETED);
        getInstanceFromChildren(task).ifPresent(instance -> {
            tasks.remove(instance);
            addTasksToGridPane();
        });
    }

    @Override
    public void updateTask(final Task task)
    {
        //taskRepository.insert(task);
        getInstanceFromChildren(task).ifPresent(instance -> {
            gridPane.getChildren().remove(instance);
            addTasksToGridPane();
        });
    }

}
