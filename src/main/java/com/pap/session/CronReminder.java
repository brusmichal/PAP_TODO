package com.pap.session;

import com.pap.database.Status;
import com.pap.database.task.repository.TaskRepository;
import com.pap.view.alerts.AlertFactory;
import javafx.application.Platform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CronReminder {

    private final UserSession userSession;
    private final TaskRepository taskRepository;

    @Scheduled(cron="*/600 * * * * *")
    public void remindTasksToUser()
    {
        if(userSession.getUsername() != null)
        {
            final var tasks = taskRepository.getTasksByUserAndStatusAndReminded(userSession.getUsername(), Status.TO_DO,false);
            final var reminderTasks = tasks.stream().filter(task -> task.getDueDate().isBefore(LocalDateTime.now())).collect(Collectors.toList()); // getReminder
            if(!reminderTasks.isEmpty())
            {
                final var taskNames = reminderTasks.stream().map(task -> "- " +task.getTitle()).collect(Collectors.joining("\n"));
                Platform.runLater(() -> AlertFactory.createErrorDialog("TASKS NEAR DUE DATE !!!", taskNames).show());
                log.info("Tasks were past their due time");
            }

        }

    }
}
