package com.pap.database.task;

import com.pap.database.Status;
import com.pap.database.task.repository.TaskRepository;

import java.time.LocalDateTime;

public class TaskProvider {

    public void populateDatabase(final TaskRepository taskRepository)
    {
        final var task1 = Task.builder()
                .withId("1")
                .withStatus(Status.TO_DO)
                .withTitle("Title")
                .withDescription("None")
                .withCreationTime(LocalDateTime.now().plusHours(2))
                .withDueDate(LocalDateTime.now().plusHours(8))
                .withUser("adam")
                .withReminded(false)
                .build();

        final var task2 = Task.builder()
                .withId("2")
                .withStatus(Status.DONE)
                .withTitle("Second")
                .withDescription("Some")
                .withCreationTime(LocalDateTime.now().plusHours(2))
                .withDueDate(LocalDateTime.now().plusHours(8))
                .withUser("adam")
                .withReminded(true)
                .build();

        final var task3 = Task.builder()
                .withId("3")
                .withStatus(Status.TO_DO)
                .withTitle("Title")
                .withDescription("None")
                .withCreationTime(LocalDateTime.now().plusHours(2))
                .withDueDate(LocalDateTime.now().plusHours(8))
                .withUser("bogdan")
                .withReminded(true)
                .build();

        final var task4 = Task.builder()
                .withId("4")
                .withStatus(Status.TO_DO)
                .withTitle("Title")
                .withDescription("None")
                .withCreationTime(LocalDateTime.now().plusHours(2))
                .withDueDate(LocalDateTime.now().plusHours(8))
                .withUser("bogdan")
                .withReminded(false)
                .build();

        final var task5 = Task.builder()
                .withId("5")
                .withStatus(Status.TO_DO)
                .withTitle("Title")
                .withDescription("None")
                .withCreationTime(LocalDateTime.now().plusHours(2))
                .withDueDate(LocalDateTime.now().plusHours(8))
                .withUser("bogdan")
                .withReminded(true)
                .build();

        final var task6 = Task.builder()
                .withId("6")
                .withStatus(Status.TO_DO)
                .withTitle("Title")
                .withDescription("None")
                .withCreationTime(LocalDateTime.now().plusHours(6))
                .withDueDate(LocalDateTime.now().plusHours(8))
                .withUser("bogdan")
                .withReminded(true)
                .build();

        taskRepository.insert(task1);
        taskRepository.insert(task2);
        taskRepository.insert(task3);
        taskRepository.insert(task4);
        taskRepository.insert(task5);
        taskRepository.insert(task6);
    }
}
