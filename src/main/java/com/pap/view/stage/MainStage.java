package com.pap.view.stage;

import com.pap.database.task.repository.TaskRepository;
import com.pap.sort.SortingProperties;
import com.pap.view.pages.DonePage;
import com.pap.view.pages.TaskPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Main component in which all graphics is rooted.
 */
@Component
public class MainStage {
    private static final String BUTTON_ON = "switch-button-on";
    private static  final String BUTTON_OFF = "switch-button-off";

    private final TaskRepository taskRepository;
    private final SortingProperties sortingProperties;
    private Node currentPage;
    private List<Button> buttons;

    @FXML
    private HBox root;
    @FXML
    private Button taskButton;
    @FXML
    private Button doneButton;
    @FXML
    private Button reportsButton;



    @Autowired
    public MainStage(final TaskRepository taskRepository, final SortingProperties sortingProperties)
    {
        this.taskRepository = taskRepository;
        this.sortingProperties = sortingProperties;
        this.currentPage = new TaskPage(taskRepository);
    }

    @FXML
    public void initialize()
    {
        root.getChildren().add(currentPage);
        setTasksPage();
        buttons = List.of(taskButton,doneButton,reportsButton);
    }
    public void goToTaskPage(final ActionEvent event)
    {
        event.consume();
        pickPage(taskButton);
        setTasksPage();
    }

    public void goToDonePage(final ActionEvent event)
    {
        event.consume();
        pickPage(doneButton);
        setDonePage();
    }

    public void goToReportPage(final ActionEvent event)
    {
        event.consume();
        pickPage(reportsButton);
        setReportPage();
    }

    private void setTasksPage()
    {
        root.getChildren().remove(currentPage);
        currentPage = new TaskPage(taskRepository);
        root.getChildren().add(currentPage);
    }

    private void setDonePage()
    {
        root.getChildren().remove(currentPage);
        currentPage = new DonePage(taskRepository,sortingProperties);
        root.getChildren().add(currentPage);
    }

    private void setReportPage()
    {
        root.getChildren().remove(currentPage);
        currentPage = new DonePage(taskRepository,sortingProperties);
        root.getChildren().add(currentPage);
    }

    private void pickPage(final Button buttonPressed)
    {
        buttonPressed.getStyleClass().clear();
        buttonPressed.getStyleClass().add(BUTTON_ON);
        buttons.stream()
                .filter(button -> !button.equals(buttonPressed))
                .forEach(button-> {
                    button.getStyleClass().clear();
                    button.getStyleClass().add(BUTTON_OFF);
                });
    }

}
