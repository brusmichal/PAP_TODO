package com.pap.database.task.repository;

import com.pap.database.Status;
import com.pap.database.task.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task,String> {
    List<Task> getTasksByUserAndStatus(final String username, final Status status);
}
