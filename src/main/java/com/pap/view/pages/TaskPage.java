package com.pap.view.pages;

import com.pap.database.Status;
import com.pap.database.task.Task;
import com.pap.database.task.repository.TaskRepository;
import com.pap.session.UserSession;
import com.pap.view.task.NewTask;
import com.pap.view.task.TaskInstance;
import com.pap.view.task.buttons.TaskEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class TaskPage extends DefaultPage<NewTask> implements TaskEvents {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskPage(final TaskRepository taskRepository, final UserSession userSession)
    {
        super(userSession);
        this.taskRepository = taskRepository;
        this.lowerPanel = new NewTask(this,userSession);
        setUpVBox();

        if(userSession.getUsername() != null)
        {
            taskRepository.getTasksByUserAndStatus(userSession.getUsername(), Status.TO_DO)
                    .forEach(this::addTaskToGridPane);
            log.info("Task page fully loaded and is composed of {} tasks", tasks.size());
        }
    }


    @Override
    public void addTask(final Task task)
    {
        try
        {
            taskRepository.insert(task);
            addTaskToGridPane(task);
        }
        catch (final DataAccessException e)
        {
            log.error("Incorrect task name. Names must be distinct {} !!!", task.getTitle());
        }
    }

    private void addTaskToGridPane(final Task task)
    {
        final var taskInstance = TaskInstance.fromTask(task,this);
        addTaskWithStyle(taskInstance);
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
         taskRepository.save(task);
    }

    @Override
    public void removeTask(final Task task)
    {
        task.setStatus(Status.DELETED);
        taskRepository.save(task);
        getInstanceFromChildren(task).ifPresent(instance -> {
            tasks.remove(instance);
            addTasksToGridPane();
        });
    }

    @Override
    public void updateTask(final Task task)
    {
        task.reminded(!task.reminded());
        taskRepository.save(task);
    }

}
