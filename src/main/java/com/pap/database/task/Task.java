package com.pap.database.task;

import com.pap.database.Status;
import com.pap.database.user.User;
import lombok.Builder;
import lombok.Data;

import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Data that will
 */
@Data
@Document
@Builder(setterPrefix = "with")
public class Task {
    @Id
    private String id;

    // Phase in which given task is in
    @NonNull
    private Status status;

    // Title briefly describing the issue of that the task tackles
    @NonNull
    private String Title;

    // Full description of the task, that will help to understand how to do it etc.
    private String description;

    // Time when the task was created
    @NonNull
    private final LocalDateTime creationTime;

    // Changeable time that marks the time until the task should be finished
    @NonNull
    private LocalDateTime dueDate;

    // Real time when the task was finished
    private LocalDateTime endTime;

    // Owner of the task
    private final String user;
}
