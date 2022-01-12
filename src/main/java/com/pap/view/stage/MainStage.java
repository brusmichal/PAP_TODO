package com.pap.view.stage;

import com.pap.database.task.repository.TaskRepository;
import com.pap.session.UserSession;
import com.pap.sort.SortingProperties;
import com.pap.view.pages.DonePage;
import com.pap.view.pages.ReportsPage;
import com.pap.view.pages.TaskPage;
import com.pap.view.task.buttons.custom.ActiveButton;
import com.pap.view.task.buttons.custom.HighlightableButton;
import com.pap.view.task.buttons.factory.ButtonFactory;
import com.pap.view.task.buttons.factory.ButtonTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Main component in which all graphics is rooted.
 */
@Slf4j
@Component
public class MainStage {
    private static final String BUTTON_ON = "switch-button-on";
    private static  final String BUTTON_OFF = "switch-button-off";

    private final TaskRepository taskRepository;
    private final UserSession userSession;
    private final SortingProperties sortingProperties;

    private Node currentPage;
    private List<ActiveButton> buttons;

    @FXML
    private HBox root;
    @FXML
    private VBox vBox;

    private final ActiveButton taskButton;
    private final ActiveButton doneButton;
    private final ActiveButton reportsButton;
    private final HighlightableButton logout;

    private final Region emptyRegion;

    @Autowired
    public MainStage(final TaskRepository taskRepository, final UserSession userSession, final SortingProperties sortingProperties)
    {
        this.taskRepository = taskRepository;
        this.userSession = userSession;
        this.sortingProperties = sortingProperties;
        this.currentPage = new TaskPage(taskRepository,userSession);

        taskButton = ButtonFactory.createActiveButtonTurnedOn(ButtonTypes.TASKS,ButtonTypes.ACTIVE_STYLE);
        doneButton = ButtonFactory.createActiveButton(ButtonTypes.DONE,ButtonTypes.ACTIVE_STYLE);
        reportsButton = ButtonFactory.createActiveButton(ButtonTypes.REPORTS,ButtonTypes.ACTIVE_STYLE);
        logout = ButtonFactory.createButtonWithStyle(ButtonTypes.LOGOUT);

        taskButton.setActive(true);
        taskButton.setOnAction(this::goToTaskPage);
        doneButton.setOnAction(this::goToDonePage);
        reportsButton.setOnAction(this::goToReportPage);

        logout.setOnAction(this::exitApplication);

        emptyRegion = new Region();
        VBox.setVgrow(emptyRegion, Priority.ALWAYS);
    }

    @FXML
    public void initialize()
    {
        root.getChildren().add(currentPage);
        setTasksPage();
        buttons = List.of(taskButton,doneButton,reportsButton);
        vBox.getChildren().addAll(buttons);
        vBox.getChildren().add(emptyRegion);
        vBox.getChildren().add(logout);
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
        currentPage = new TaskPage(taskRepository, userSession);
        root.getChildren().add(currentPage);
    }

    private void setDonePage()
    {
        root.getChildren().remove(currentPage);
        currentPage = new DonePage(taskRepository,sortingProperties, userSession);
        root.getChildren().add(currentPage);
    }

    private void setReportPage()
    {
        root.getChildren().remove(currentPage);
        currentPage = new ReportsPage(taskRepository, userSession);
        root.getChildren().add(currentPage);
    }

    private void pickPage(final ActiveButton buttonPressed)
    {
        buttonPressed.getStyleClass().clear();
        buttonPressed.getStyleClass().add(BUTTON_ON);
        buttonPressed.setActive(true);
        buttons.stream()
                .filter(button -> !button.equals(buttonPressed))
                .forEach(button-> {
                    button.getStyleClass().clear();
                    button.getStyleClass().add(BUTTON_OFF);
                    button.setActive(false);
                });
        log.info("Page during change ...");
    }


    public void exitApplication(final ActionEvent event)
    {
        final var stage = (Stage) logout.getScene().getWindow();
        stage.close();

        final var lastLogin = userSession.getLastLoginRepository().findFirstByOrderByLastLoginTimeDesc();
        lastLogin.setLogin(false);
        userSession.getLastLoginRepository().save(lastLogin);
        log.info("User {} was successfully log out",lastLogin.getUsername());
    }

    public void highlightLogoutButton(final MouseEvent event)
    {
        logout.getStyleClass().clear();
        logout.getStyleClass().add("highlight-log-out-button");
    }

    public void rollbackLogoutButton(final MouseEvent event)
    {
        logout.getStyleClass().clear();
        logout.getStyleClass().add("log-out-button");
    }

}
