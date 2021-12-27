package com.pap.view.pages;

import com.pap.database.Status;
import com.pap.database.task.Task;
import com.pap.database.task.repository.TaskRepository;
import com.pap.session.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class used to fetch data from database and prepare it to be displayed in ReportPage
 */
@Component
public class ReportDataProvider {
    private final TaskRepository taskRepository;
    private final UserSession userSession;

    @Autowired
    public ReportDataProvider(final TaskRepository taskRepository, final UserSession userSession)
    {
        this.taskRepository = taskRepository;
        this.userSession = userSession;
    }

    public Map<Status, Long> getGroupedTasks()
    {
        final var tasks = taskRepository.getTasksByUser(userSession.getUsername());
        return groupTasks(tasks);
    }

    public Map<Status, Long> getGroupedTasksAfterCreationDate(final LocalDateTime creationDate)
    {
        final var tasks = taskRepository.getTasksByUserAndCreationTimeAfter(userSession.getUsername(),creationDate);
        return groupTasks(tasks);
    }

    public Map<Status,Task> getFastestDoneAfterCreationDate(final LocalDateTime creationDate)
    {
        return getTaskWithExtremeTaskLifespanAfterCreationDate(creationDate,true);
    }

    public Map<Status,Task> getSlowestDoneAfterCreationDate(final LocalDateTime creationDate)
    {
        return getTaskWithExtremeTaskLifespanAfterCreationDate(creationDate,false);
    }

    private Map<Status,Task> getTaskWithExtremeTaskLifespanAfterCreationDate(final LocalDateTime creationDate, final boolean first)
    {
        final var map = new HashMap<Status, Task>();
        final var tasks = taskRepository.getTasksByUserAndCreationTimeAfter(userSession.getUsername(),creationDate);
        final var groupedTasks = tasks.stream()
                .collect(Collectors.groupingBy(
                        Task::getStatus,
                        Collectors.toList()));
        groupedTasks.forEach((key, value) -> {
            final var index = first ? 0 : value.size() - 1;
            value.sort(Comparator.comparingLong(
                    x -> x.getEndTime().minusSeconds(x.getCreationTime().toEpochSecond(ZoneOffset.UTC)).toEpochSecond(ZoneOffset.UTC)));
            map.put(key, value.get(index));
        });
        return map;
    }

    private Map<Status, Long> groupTasks(final List<Task> tasks)
    {
        return tasks.stream()
                .collect(Collectors.groupingBy(
                        Task::getStatus,
                        Collectors.counting()));
    }
}
