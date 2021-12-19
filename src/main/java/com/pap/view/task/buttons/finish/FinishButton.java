package com.pap.view.task.buttons.finish;

import com.pap.database.task.Task;
import com.pap.view.task.buttons.custom.DisabledButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class FinishButton extends DisabledButton implements EventHandler<ActionEvent> {
    private static final String FINISH_STYLE = "finish-button";
    private final Task task;
    private final Finish finish;

    public FinishButton(final Task task, final Finish finish)
    {
        super();
        this.task = task;
        this.finish = finish;
        this.setOnAction(this);
        this.getStyleClass().add(FINISH_STYLE);
    }

    @Override
    public void handle(final ActionEvent event)
    {
        finish.finishTask(task);
    }
}
