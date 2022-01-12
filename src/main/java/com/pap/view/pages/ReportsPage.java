package com.pap.view.pages;

import com.pap.database.Status;
import com.pap.database.task.Task;
import com.pap.database.task.repository.TaskRepository;
import com.pap.session.UserSession;
import com.pap.view.reports.Chart;
import com.pap.view.reports.PeriodPanel;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component

public class ReportsPage extends VBox {
    protected final UserSession userSession;
    private final TaskRepository taskRepository;
    protected final VBox vBox;
    //protected final List<Task> history;
    protected final List<Chart> charts;
    protected final PeriodPanel lowerPanel;
    protected final LocalDateTime dateSince;
    private final ReportDataProvider data;

    @Autowired
    public ReportsPage(final TaskRepository taskRepository, final UserSession userSession)
    {
        this.userSession = userSession;
        this.taskRepository = taskRepository;
        this.vBox= new VBox();
        this.charts = new ArrayList<>();
        this.lowerPanel = new PeriodPanel();
        this.dateSince = LocalDateTime.now().minusMonths(1);
        this.data = new ReportDataProvider(taskRepository, userSession);
        charts.add(new Chart(data, dateSince));
        vBox.getStyleClass().add("default-grid-pane");
        setUpVBox();

//        this.history = taskRepository.getTasksByUserAndStatus(userSession.getUsername(), Status.DONE);
//        log.info("History for ReportsPage consist of {} done tasks.", history.size());



    }

    protected void setUpVBox()
    {
        getChildren().addAll(charts);
        getChildren().add(lowerPanel);
    }

}
