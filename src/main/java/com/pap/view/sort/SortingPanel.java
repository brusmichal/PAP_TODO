package com.pap.view.sort;

import com.pap.sort.SortingProperties;
import com.pap.view.task.buttons.custom.HighlightableButton;
import com.pap.view.task.buttons.factory.ButtonFactory;
import com.pap.view.task.buttons.factory.ButtonTypes;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class SortingPanel extends VBox {
    private final Label sortBy;
    private final List<HighlightableButton> sortingOptions;

    public SortingPanel(final SortingProperties sortingProperties,final Sort sort)
    {
        this.sortingOptions = new ArrayList<>();
        sortingProperties.getComparators().forEach((key, value) -> {
            final var button = ButtonFactory.createButtonWithStyle(ButtonTypes.SORT);
            button.setText(key);
            button.setOnAction((event) -> sort.sortBy(value));
            button.getStyleClass().add("sort-button");
            sortingOptions.add(button);
        });

        sortBy = new Label("Sort by...");
        sortBy.getStyleClass().add("sort-by");

        HBox.setHgrow(sortBy, Priority.ALWAYS);
        this.getChildren().add(sortBy);

        this.setOnMouseEntered((event) -> {
            this.getChildren().remove(sortBy);
            this.getChildren().addAll(sortingOptions);
            this.getChildren().add(sortBy);
        });

        this.setOnMouseExited((event) -> this.getChildren().removeAll(sortingOptions));
    }
}
