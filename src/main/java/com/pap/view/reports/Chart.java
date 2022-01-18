package com.pap.view.reports;

import com.pap.database.Status;
import com.pap.view.pages.ReportDataProvider;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.time.LocalDateTime;

public class Chart {
    private final CategoryAxis xAxis;
    private final NumberAxis yAxis;
    private final BarChart<String, Number> barChart;
    private final ReportDataProvider data;

    public LocalDateTime getDateSince() {
        return dateSince;
    }

    private final LocalDateTime dateSince;


    public Chart(final ReportDataProvider data, final LocalDateTime dateSince) {
        this.xAxis = new CategoryAxis();
        this.yAxis = new NumberAxis();
        setUpAxes();
        this.barChart = new BarChart<>(xAxis, yAxis);
        this.data = data;
        this.dateSince = dateSince;
        loadChartData();
        barChart.getStyleClass().add("chart");
    }

    public BarChart<String, Number> getBarChart() {
        return barChart;
    }

    private void loadChartData() {
        XYChart.Series <String, Number> dataAllTasks = new XYChart.Series<>();
        XYChart.Series <String, Number> dataDoneTasks = new XYChart.Series<>();
        XYChart.Series <String, Number> dataonTimeTasks = new XYChart.Series<>();
        XYChart.Series <String, Number> dataLateTasks = new XYChart.Series<>();

        data.getGroupedTasksAfterCreationDate(dateSince).forEach((key, value) -> System.out.println("Key: " + key + ", " + "value: " + value ));
        long doneTasksNumber = data.getGroupedTasksAfterCreationDate(dateSince).getOrDefault(Status.DONE, 0L);
        long unfinishedTasksNumber = data.getGroupedTasksAfterCreationDate(dateSince).getOrDefault(Status.TO_DO, 0L);
        long lateTasksNumber = data.getLateGroupedTasksAfterCreationDate(dateSince).getOrDefault(Status.TO_DO, 0L);
        long onTimeTaskNumber = unfinishedTasksNumber - lateTasksNumber;
        long allTasksNumber = doneTasksNumber + unfinishedTasksNumber;

        dataDoneTasks.getData().add(new XYChart.Data<>("", doneTasksNumber));
        dataonTimeTasks.getData().add(new XYChart.Data<>("", onTimeTaskNumber));
        dataLateTasks.getData().add(new XYChart.Data<>("", lateTasksNumber));
        dataAllTasks.getData().add(new XYChart.Data<>("", allTasksNumber));

        dataDoneTasks.setName("Done");
        dataonTimeTasks.setName("Unfinished");
        dataLateTasks.setName("Late");
        dataAllTasks.setName("All");

        barChart.getData().add(dataAllTasks);
        barChart.getData().add(dataDoneTasks);
        barChart.getData().add(dataonTimeTasks);
        barChart.getData().add(dataLateTasks);

    }

    private void setUpAxes() {
        xAxis.setLabel("Status");
        yAxis.setLabel("Number");
        xAxis.getCategories().addAll("Task status");

    }
}
