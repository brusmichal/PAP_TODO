package com.pap.view.task;

import com.pap.database.task.Task;
import com.pap.view.task.buttons.TaskEvents;
import com.pap.view.task.buttons.finish.FinishButton;
import com.pap.view.task.buttons.remove.Remove;
import com.pap.view.task.buttons.remove.RemoveButton;
import com.pap.view.task.buttons.update.UpdateButton;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder(setterPrefix = "with", access = AccessLevel.PRIVATE)
public class TaskInstance extends HBox {
    private static final String TASK_STYLE = "instance";
    private static final String VBOX_STYLE = "instance";

    private final Task task;
    private final Label title;
    private final Label date;
    private final Button removeButton;
    private final Button changeDueDateButton;
    private final Button finishTaskButton;

    /**
     * Generate graphical representation of task, which can be deleted, altered or set as completed.
     * @param task raw object
     * @param handler operations to be handled after button interaction
     * @return graphical representation of task
     */
    public static TaskInstance fromTask(final Task task, final TaskEvents handler)
    {
        final var beforeDueDate = task.getDueDate().isBefore(LocalDateTime.now());
        final var title = generateTitle(task);
        final var date = generateDate(task, beforeDueDate);
        final var removeButton = new RemoveButton(task, handler);
        final var changeDueDateButton =  new UpdateButton(task, handler);
        final var finishTaskButton = new FinishButton(task, handler);

        return TaskInstance.builder()
                .withTask(task)
                .withTitle(title)
                .withDate(date)
                .withRemoveButton(removeButton)
                .withChangeDueDateButton(changeDueDateButton)
                .withFinishTaskButton(finishTaskButton)
                .build()
                .addToNode();
    }

    /**
     * Generate graphical representation of the done task (previously marked as done in page
     * {@link com.pap.view.pages.TaskPage})
     * @param task raw object
     * @param remove handler for action of task deletion
     * @return graphical representation of done task
     */
    public static TaskInstance ofDoneTask(final Task task, final Remove remove)
    {
        final var title = generateTitle(task);
        final var date = generateFullInformation(task);
        final var removeButton = new RemoveButton(task, remove);

        return TaskInstance.builder()
                .withTask(task)
                .withTitle(title)
                .withDate(date)
                .withRemoveButton(removeButton)
                .build()
                .setDoneTask();
    }

    private static Label generateTitle(final Task task)
    {
        final var title =  new Label(task.getTitle());
        title.setText(task.getTitle());
        title.setTextAlignment(TextAlignment.CENTER);
        return title;
    }

    private static Label generateDate(final Task task, final Boolean before)
    {
        final var date = task.getDueDate().format(DateTimeFormatter.ISO_DATE);
        final var dateLabel = new Label(date);
        final var style = getStyle(before);
        dateLabel.getStyleClass().add(style);

        return dateLabel;
    }

    private static Label generateFullInformation(final Task task)
    {
        final var date = "Due date: " + task.getDueDate().format(DateTimeFormatter.ISO_DATE) + "  Creation time: " + task.getCreationTime().format(DateTimeFormatter.ISO_DATE);
        final var dateLabel = new Label(date);
        final var style = getStyle(null);
        dateLabel.getStyleClass().add(style);

        return dateLabel;
    }

    private TaskInstance addToNode()
    {
        setDefaultTaskInstance();

        setMargin(removeButton, new Insets(20,0,20,0));
        setMargin(changeDueDateButton, new Insets(20,0,20,10));
        setMargin(finishTaskButton, new Insets(20,20,20,10));

        this.getChildren().add(removeButton);
        this.getChildren().add(changeDueDateButton);
        this.getChildren().add(finishTaskButton);

        removeButton.getOnMouseExited().handle(null);
        changeDueDateButton.getOnMouseExited().handle(null);
        finishTaskButton.getOnMouseExited().handle(null);
        return this;
    }

    private TaskInstance setDoneTask()
    {
        setDefaultTaskInstance();
        setMargin(removeButton, new Insets(20,0,20,40));

        this.getChildren().add(removeButton);
        removeButton.getOnMouseExited().handle(null);
        return this;
    }

    private void setDefaultTaskInstance()
    {
        final var vbox = new VBox();
        vbox.getChildren().add(title);
        vbox.getChildren().add(date);
        vbox.getStyleClass().add(VBOX_STYLE);

        this.getChildren().add(vbox);
        this.getStyleClass().add(TASK_STYLE);
    }

    private static String getStyle(final Boolean before)
    {
        if(before == null)
        {
            return "neutral-date";
        }
        else if(before)
        {
            return "over-date";
        }
        return "before-date";
    }

}
