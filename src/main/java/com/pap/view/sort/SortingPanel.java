package com.pap.view.sort;

import com.pap.sort.SortingProperties;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class SortingPanel extends VBox {
    private final Label sortBy;
    private final List<Button> sortingOptions;

    public SortingPanel(final SortingProperties sortingProperties,final Sort sort)
    {
        this.sortingOptions = new ArrayList<>();
        sortingProperties.getComparators().forEach((key, value) -> {
            final var button = new Button(key);
            button.setOnAction((event) -> sort.sortBy(value));
            button.getStyleClass().add("sort-button");
            sortingOptions.add(button);
        });

        sortBy = new Label("Sort by...");
        sortBy.getStyleClass().add("sort-by");

        this.getChildren().add(sortBy);

        this.setOnMouseEntered((event) -> {
            this.getChildren().remove(sortBy);
            this.getChildren().addAll(sortingOptions);
            this.getChildren().add(sortBy);
        });

        this.setOnMouseExited((event) -> this.getChildren().removeAll(sortingOptions));
    }
}
