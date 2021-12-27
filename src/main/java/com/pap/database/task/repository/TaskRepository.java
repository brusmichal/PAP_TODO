package com.pap.database.task.repository;

import com.pap.database.Status;
import com.pap.database.task.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task,String> {
    List<Task> getTasksByUser(final String username);
    List<Task> getTasksByUserAndStatus(final String username, final Status status);
    List<Task> getTasksByUserAndStatusAndReminded(final String username, final Status status,final boolean reminded);

    List<Task> getTasksByUserAndCreationTimeAfter(final String username, final LocalDateTime creationTime);
    }
