package com.pap.view.task.buttons.remove;

import com.pap.database.task.Task;
import com.pap.view.task.buttons.custom.DisabledButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class RemoveButton extends DisabledButton implements EventHandler<ActionEvent> {
    private static final String REMOVE_BUTTON = "remove-button";

    private final Task task;
    private final Remove remove;
    public RemoveButton(final Task task, final Remove remove)
    {
        super();
        this.task = task;
        this.remove = remove;
        this.setOnAction(this);
        this.getStyleClass().add(REMOVE_BUTTON);
//        this.setMaxWidth(Double.MAX_VALUE);
//        final var image = new Image(getClass().getResourceAsStream(REMOVE_IMAGE));
//        final var imageView = new ImageView(image);
//        this.setGraphic(imageView);
//        this.setContentDisplay(ContentDisplay.CENTER);
    }

    @Override
    public void handle(final ActionEvent event)
    {
        remove.removeTask(task);
    }
}
