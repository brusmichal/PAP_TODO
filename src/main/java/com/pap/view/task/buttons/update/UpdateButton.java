package com.pap.view.task.buttons.update;

import com.pap.database.task.Task;
import com.pap.view.task.buttons.custom.DisabledButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class UpdateButton extends DisabledButton implements EventHandler<ActionEvent> {
    private static final String UPDATE_STYLE = "update-button";
    private final Task task;
    private final Update update;

    public UpdateButton(final Task task, final Update update)
    {
        super();
        this.task = task;
        this.update = update;
        this.setOnAction(this);
        this.getStyleClass().add(UPDATE_STYLE);
    }

    @Override
    public void handle(final ActionEvent event)
    {
        update.updateTask(task);
    }
}
