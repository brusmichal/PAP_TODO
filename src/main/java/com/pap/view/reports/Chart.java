package com.pap.view.reports;

import com.pap.database.Status;
import com.pap.database.task.repository.TaskRepository;
import com.pap.session.UserSession;
import com.pap.view.pages.ReportDataProvider;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import lombok.NonNull;

import java.time.LocalDateTime;

public class Chart extends Node {
    private final CategoryAxis xAxis;
    private final NumberAxis yAxis;
    private final BarChart<String, Number> barChart;
    private final ReportDataProvider data;
    private final LocalDateTime dateSince;

    public Chart(final ReportDataProvider data, final LocalDateTime dateSince) {
        this.xAxis = new CategoryAxis();
        this.yAxis = new NumberAxis();
        setUpAxes();
        this.barChart = new BarChart<>(xAxis, yAxis);
        this.data = data;
        this.dateSince = dateSince;
       // loadChartData();
    }

    private void loadChartData() {
        XYChart.Series <String, Number> dataAllTasks = new XYChart.Series<>();
        XYChart.Series <String, Number> dataDoneTasks = new XYChart.Series<>();
        XYChart.Series <String, Number> dataUnfinishedTasks = new XYChart.Series<>();

        data.getGroupedTasks().forEach((key, value) -> System.out.println("Key: " + key + ", " + "value: " + value ));
        long doneTasksNumber = data.getGroupedTasksAfterCreationDate(dateSince).get(Status.DONE);
        long unfinishedTasksNumber = data.getGroupedTasksAfterCreationDate(dateSince).get(Status.TO_DO);
        long allTasksNumber = doneTasksNumber + unfinishedTasksNumber;

        dataDoneTasks.getData().add(new XYChart.Data<>("All", doneTasksNumber));
        dataUnfinishedTasks.getData().add(new XYChart.Data<>("All", unfinishedTasksNumber));
        dataAllTasks.getData().add(new XYChart.Data<>("All", allTasksNumber));

    }

    private void setUpAxes() {
        xAxis.setLabel("Status");
        yAxis.setLabel("Number");
        xAxis.getCategories().addAll("All", "Done", "Unfinished");

    }
}
