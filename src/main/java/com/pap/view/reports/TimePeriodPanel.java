package com.pap.view.reports;

import com.pap.view.task.buttons.custom.HighlightableButton;
import com.pap.view.task.buttons.factory.ButtonFactory;
import com.pap.view.task.buttons.factory.ButtonTypes;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class TimePeriodPanel extends VBox {
    private Label reportBy;
    private final List<HighlightableButton> timePeriodOptions;
    private final UpdateChart updateChart;
    private LocalDateTime customDate;
    private final DatePicker datePicker;

    private boolean isCustomClicked = false;

    public TimePeriodPanel(final UpdateChart updateChart) {
        this.updateChart = updateChart;
        this.timePeriodOptions = new ArrayList<>();
        this.customDate = LocalDateTime.now();
        this.datePicker = new DatePicker(LocalDate.now());
        this.setAlignment(Pos.CENTER);
        addPeriodButtons();
        addCustomButton();
        setUpPanel();
    }

    private void setUpPanel() {
        reportBy = new Label("Choose report range:");
        reportBy.getStyleClass().add("report-by");

        HBox.setHgrow(reportBy, Priority.ALWAYS);
        this.getChildren().add(reportBy);

        this.setOnMouseEntered((event) -> {
            if (!isCustomClicked)
            {
                this.getChildren().remove(reportBy);
                this.getChildren().addAll(timePeriodOptions);
                this.getChildren().add(reportBy);
            }
        });

        this.setOnMouseExited((event) -> {
            if(!isCustomClicked)
            {
                this.getChildren().removeAll(timePeriodOptions);
                this.getChildren().remove(datePicker);
            }
        });
    }

    private void addPeriodButtons() {
        LinkedHashMap<String, LocalDateTime> timePeriods = new LinkedHashMap<>();
        timePeriods.put("Day", LocalDateTime.now().minusDays(1));
        timePeriods.put("Week", LocalDateTime.now().minusWeeks(1));
        timePeriods.put("Month", LocalDateTime.now().minusMonths(1));
        timePeriods.forEach((key, value) -> {
                    final var button = ButtonFactory.createButtonWithStyle(ButtonTypes.PERIOD);
                    button.setText(key);
                    button.setOnAction((event) -> {
                        updateChart.updateChartArea(value);
                        isCustomClicked = false;
                        if(getChildren().contains(datePicker))
                        {
                            getChildren().remove(datePicker);
                        }
                    });
                    button.getStyleClass().add("time-period-button");
                    timePeriodOptions.add(button);


                }
        );
    }

    private void addCustomButton() {
        customDate = LocalDateTime.now();
        final var button = ButtonFactory.createButtonWithStyle(ButtonTypes.PERIOD);
        button.setText("Custom...");
        button.setOnAction((event) -> {
            if(!isCustomClicked)
            {
                isCustomClicked = true;
                datePicker.getStyleClass().add("date-selector");
                if(this.getChildren().contains(reportBy)) {
                    this.getChildren().remove(reportBy);
                    this.getChildren().add(datePicker);
                    this.getChildren().add(reportBy);
            }
            }
            else {
                isCustomClicked = false;
                if(getChildren().contains(datePicker))
                {
                    getChildren().remove(datePicker);
                }
            }
        });
        datePicker.setOnAction(event -> {
            customDate = datePicker.getValue().atTime(0,0);
            updateChart.updateChartArea(customDate);
        });
        button.getStyleClass().add("time-period-button");
        timePeriodOptions.add(button);
        
    }
}
