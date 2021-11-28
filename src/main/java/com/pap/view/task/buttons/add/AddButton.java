package com.pap.view.task.buttons.add;

import com.pap.database.task.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class AddButton extends Button implements EventHandler<ActionEvent> {
    private static final String ADD_STYLE = "add-button";
    private final Task task;
    private final Add add;

    public AddButton(final Task task, final Add add)
    {
        super();
        this.task = task;
        this.add = add;
        this.getStyleClass().add(ADD_STYLE);
        this.setOnAction(this);
    }

    @Override
    public void handle(final ActionEvent event)
    {
        add.addTask(task);
    }
}
