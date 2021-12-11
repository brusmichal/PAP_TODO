package com.pap.view.pages;

import com.pap.database.task.Task;
import com.pap.session.UserSession;
import com.pap.view.task.TaskInstance;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class  DefaultPage<T extends Node> extends VBox {
    protected final UserSession userSession;
    protected final List<TaskInstance> tasks;
    protected final GridPane gridPane;
    protected  T lowerPanel;
    protected final ScrollPane scrollPane;

    protected DefaultPage(final UserSession userSession)
    {
        this.userSession = userSession;
        this.tasks = new ArrayList<>();
        this.gridPane = new GridPane();
        this.scrollPane = new ScrollPane();

        gridPane.getStyleClass().add("default-grid-pane");
        setUpScrollPane();
    }

    private void setUpScrollPane()
    {
        scrollPane.setContent(gridPane);
        scrollPane.setFitToWidth(true);
    }

    protected void setUpVBox()
    {
        getChildren().add(scrollPane);
        getChildren().add(lowerPanel);
    }

    protected Optional<TaskInstance> getInstanceFromChildren(final Task task)
    {
        return tasks.stream()
                .filter(taskInstance -> taskInstance.getTask().getId().equals(task.getId()))
                .findFirst();
    }

    protected void addTasksToGridPane()
    {
        gridPane.getChildren().clear();
        tasks.forEach(task -> gridPane.add(task, 0, tasks.indexOf(task)));
    }
}
