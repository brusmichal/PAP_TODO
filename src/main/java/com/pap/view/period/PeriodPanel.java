package com.pap.view.period;

import com.pap.view.task.buttons.custom.HighlightableButton;
import com.pap.view.task.buttons.factory.ButtonFactory;
import com.pap.view.task.buttons.factory.ButtonTypes;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PeriodPanel extends VBox {
    private final Label reportBy;
    private final List<HighlightableButton> periodButtons;
    private final Map<String, LocalDateTime> periodOptions;
    private LocalDateTime customDT;


    public PeriodPanel() {
        periodButtons = new ArrayList<>();
        periodOptions = Map.of("Week", LocalDateTime.now().minusWeeks(1), "Month", LocalDateTime.now().minusMonths(1),
                "Year", LocalDateTime.now().minusYears(1), "Custom...", customDT = LocalDateTime.now());
        periodOptions.forEach((key, value) -> {
            final var button = ButtonFactory.createButtonWithStyle(ButtonTypes.PERIOD);
            button.setText(key);
            //button.setOnAction((event) -> ();
            button.getStyleClass().add("period-button");
            periodButtons.add(button);
                }
        );
        reportBy = new Label("Choose report range:");
        reportBy.getStyleClass().add("sort-by");
        this.getChildren().add(reportBy);
        this.getChildren().addAll(periodButtons);



    }
}
