package com.pap.view.task.buttons.finish;

import com.pap.database.task.Task;
import com.pap.view.task.buttons.DisabledButton;
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
        // TODO check how to add images
//        final var image = new Image(getClass().getResourceAsStream(FINISH_IMAGE));
//        final var imageView = new ImageView(image);
//        imageView.setFitHeight(20.0);
//        imageView.setFitWidth(20.0);
//        this.setGraphic(imageView);
    }

    @Override
    public void handle(final ActionEvent event)
    {
        finish.finishTask(task);
    }
}
