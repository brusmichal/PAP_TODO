package com.pap.view.reports;

import com.pap.view.task.buttons.custom.HighlightableButton;
import com.pap.view.task.buttons.factory.ButtonFactory;
import com.pap.view.task.buttons.factory.ButtonTypes;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimePeriodPanel extends VBox {
    private final Label reportBy;
    private final List<HighlightableButton> timePeriodButtons;
    private final Map<String, LocalDateTime> timePeriodOptions;
    private LocalDateTime dateSince;
    private LocalDateTime customDT;



    public TimePeriodPanel(final UpdateChart updateChart) {
        timePeriodButtons = new ArrayList<>();
        timePeriodOptions = Map.of("Day", LocalDateTime.now().minusDays(1), "Week", LocalDateTime.now().minusWeeks(1),
                "Month", LocalDateTime.now().minusMonths(1), "Custom...", customDT = LocalDateTime.now());
        timePeriodOptions.forEach((key, value) -> {
            final var button = ButtonFactory.createButtonWithStyle(ButtonTypes.PERIOD);
            button.setText(key);
            button.setOnAction((event) -> updateChart.updateChartArea(value));
            button.getStyleClass().add("time-period-button");
            timePeriodButtons.add(button);
                }
        );
        reportBy = new Label("Choose report range:");
        reportBy.getStyleClass().add("report-by");

        HBox.setHgrow(reportBy, Priority.ALWAYS);
        this.getChildren().add(reportBy);

        this.setOnMouseEntered((event) -> {
            this.getChildren().remove(reportBy);
            this.getChildren().addAll(timePeriodButtons);
            this.getChildren().add(reportBy);
        });

        this.setOnMouseExited((event) -> this.getChildren().removeAll(timePeriodButtons));
        



    }
}
