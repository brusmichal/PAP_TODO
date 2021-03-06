package com.pap.view.task;

import com.pap.database.Status;
import com.pap.database.task.Task;
import com.pap.session.UserSession;
import com.pap.view.task.buttons.add.Add;
import com.pap.view.task.buttons.add.AddButton;
import com.pap.view.task.buttons.remove.Remove;
import com.pap.view.task.buttons.remove.RemoveButton;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class NewTask extends HBox implements Remove, Add {
    private final static String TEXT_STYLE = "task-text-field";
    private final static String CLEAR_STYLE = "clear-button";

    private final TextField textField;
    private final HourPicker hourPicker;
    private final DatePicker datePicker;
    private final Add add;
    private final UserSession userSession;

    public NewTask(final Add add,final UserSession userSession)
    {
        final var addButton = new AddButton(null, this);
        final var clearButton = new RemoveButton(null,this);

        this.textField = new TextField();
        this.hourPicker = new HourPicker();
        this.datePicker = new DatePicker();
        this.add = add;
        this.userSession = userSession;

        HBox.setHgrow(textField, Priority.ALWAYS);
        this.getChildren().addAll(textField,hourPicker,datePicker,clearButton, addButton);
        datePicker.setValue(LocalDate.now());
        clearButton.getStyleClass().clear();
        clearButton.getStyleClass().add(CLEAR_STYLE);
        clearButton.setOnMouseExited((event->{}));

        textField.getStyleClass().add(TEXT_STYLE);
        textField.setPromptText("Write your task");

        datePicker.getStyleClass().add("date-picker");
        hourPicker.getStyleClass().add("hour-picker");

    }

    @Override
    public void removeTask(final Task task)
    {
        textField.clear();
    }

    @Override
    public void addTask(final Task task)
    {
        final var hourText = hourPicker.getFormattedText();
        if(hourText.isPresent())
        {
            final var newTask = Task.builder()
                    .withTitle(textField.getText())
                    .withCreationTime(LocalDateTime.now())
                    .withDueDate(LocalDateTime.of(datePicker.getValue(), LocalTime.parse(hourText.get(), DateTimeFormatter.ISO_TIME)))
                    .withId(textField.getText())
                    .withStatus(Status.TO_DO)
                    .withUser(userSession.getUsername())
                    .build();
            log.info("New task is ready to be processed ... [{}]",newTask.getTitle());
            add.addTask(newTask);
            removeTask(newTask);
            log.info("Successfully added task named: {}", newTask.getTitle());
        }
        else
        {
            log.error("Hour was submitted in incorrect format");
        }
    }
}
