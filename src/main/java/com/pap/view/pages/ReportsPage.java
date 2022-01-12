package com.pap.view.pages;

import com.pap.database.task.repository.TaskRepository;
import com.pap.session.UserSession;
import com.pap.view.reports.Chart;
import com.pap.view.reports.TimePeriodPanel;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component

public class ReportsPage extends VBox {
    protected final UserSession userSession;
    protected final TaskRepository taskRepository;
    protected final VBox chartArea;
    protected final BorderPane borderPane;
    //protected final List<Task> history;
    protected final List<Chart> charts;
    protected final TimePeriodPanel lowerPanel;
    protected final LocalDateTime dateSince;
    private final ReportDataProvider data;

    @Autowired
    public ReportsPage(final TaskRepository taskRepository, final UserSession userSession)
    {
        this.userSession = userSession;
        this.taskRepository = taskRepository;
        this.chartArea= new VBox();
        this.borderPane = new BorderPane();
        this.charts = new ArrayList<>();
        this.dateSince = LocalDateTime.now().minusMonths(1);
        this.lowerPanel = new TimePeriodPanel();
        this.data = new ReportDataProvider(taskRepository, userSession);
        charts.add(new Chart(data, dateSince));
        setUpChartArea();
        setUpVBox();
    }


    private void setUpChartArea() {
        chartArea.getStyleClass().add("chart-area");
        chartArea.getChildren().add(charts.get(0).getChart());

    }

    protected void setUpVBox()
    {
        getChildren().add(chartArea);
        getChildren().add(lowerPanel);
    }

}
