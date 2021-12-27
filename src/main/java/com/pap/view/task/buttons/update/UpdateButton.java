package com.pap.view.task.buttons.update;

import com.pap.database.task.Task;
import com.pap.view.task.buttons.custom.DisabledButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class UpdateButton extends DisabledButton implements EventHandler<ActionEvent> {
    private static final String UPDATE_NOTIFIED_STYLE = "update-button";
    private static final String UPDATE_NO_NOTIFICATION_STYLE = "update-button-no-notification";
    private final Task task;
    private final Update update;

    public UpdateButton(final Task task, final Update update)
    {
        super();
        this.task = task;
        this.update = update;
        this.setOnAction(this);
        setStyle();
    }

    @Override
    public void handle(final ActionEvent event)
    {
        update.updateTask(task);
        setStyle();
    }

    private void setStyle()
    {
        if(task.reminded())
        {
            this.getStyleClass().remove(UPDATE_NOTIFIED_STYLE);
            this.getStyleClass().add(UPDATE_NO_NOTIFICATION_STYLE);
        }
        else
        {
            this.getStyleClass().remove(UPDATE_NO_NOTIFICATION_STYLE);
            this.getStyleClass().add(UPDATE_NOTIFIED_STYLE);
        }
    }
}
