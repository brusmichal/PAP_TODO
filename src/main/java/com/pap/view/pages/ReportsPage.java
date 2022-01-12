package com.pap.view.pages;

import com.pap.database.task.repository.TaskRepository;
import com.pap.session.UserSession;
import com.pap.view.reports.Chart;
import com.pap.view.reports.TimePeriodPanel;
import com.pap.view.reports.UpdateChart;
import javafx.scene.chart.BarChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j

public class ReportsPage extends VBox implements UpdateChart {
    protected final UserSession userSession;
    protected final TaskRepository taskRepository;
    protected final VBox chartArea;
    protected final BorderPane borderPane;
    //protected final List<Task> history;
    protected  BarChart<String, Number> chart;
    protected final TimePeriodPanel lowerPanel;
    protected final LocalDateTime dateSince;
    private final ReportDataProvider data;
    public ReportsPage(final TaskRepository taskRepository, final UserSession userSession)
    {
        this.userSession = userSession;
        this.taskRepository = taskRepository;
        this.chartArea= new VBox();
        this.borderPane = new BorderPane();

        this.dateSince = LocalDateTime.now().minusDays(1);
        this.lowerPanel = new TimePeriodPanel(this);
        this.data = new ReportDataProvider(taskRepository, userSession);
        this.chart = new Chart(data, dateSince).getBarChart();

        setUpChartArea();
        setUpVBox();
    }


    public void updateChartArea(final LocalDateTime dateSince) {
        chartArea.getChildren().remove(chart);
        chart = new Chart(data, dateSince).getBarChart();
        chartArea.getChildren().add(chart);
    }


    private void setUpChartArea() {
        chartArea.getStyleClass().add("chart-area");
        chartArea.getChildren().add(chart);
    }

    protected void setUpVBox()
    {
        getChildren().add(chartArea);
        getChildren().add(lowerPanel);
    }

}
