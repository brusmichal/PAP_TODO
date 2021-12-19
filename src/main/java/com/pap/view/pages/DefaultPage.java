package com.pap.view.pages;

import com.pap.database.task.Task;
import com.pap.session.UserSession;
import com.pap.view.task.TaskInstance;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class  DefaultPage<T extends Node> extends VBox {
    protected final UserSession userSession;
    protected final List<TaskInstance> tasks;
    protected final VBox vBox;
    protected  T lowerPanel;
    protected final ScrollPane scrollPane;

    protected DefaultPage(final UserSession userSession)
    {
        getStyleClass().add("default-p");
        this.userSession = userSession;
        this.tasks = new ArrayList<>();
        this.vBox = new VBox();
        this.scrollPane = new ScrollPane();

        vBox.getStyleClass().add("default-grid-pane");
        setUpScrollPane();
    }

    private void setUpScrollPane()
    {
        scrollPane.setContent(vBox);
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
        vBox.getChildren().clear();
        tasks.forEach(task -> vBox.getChildren().add(task));
    }
}
