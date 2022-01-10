package com.pap.view.pages;

import com.pap.database.Status;
import com.pap.database.task.Task;
import com.pap.database.task.repository.TaskRepository;
import com.pap.session.UserSession;
import com.pap.view.period.PeriodPanel;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component

public class ReportsPage extends VBox {
    protected final UserSession userSession;
    protected final VBox vBox;
    protected final List<Task> history;
    protected final PeriodPanel periodPanel;

    @Autowired
    public ReportsPage(final TaskRepository taskRepository, final UserSession userSession){
        this.userSession = userSession;
        this.vBox= new VBox();
        this.periodPanel = new PeriodPanel();
        this.history = taskRepository.getTasksByUserAndStatus(userSession.getUsername(), Status.DONE);
        log.info("History for ReportsPage consist of {} done tasks.", history.size());

        vBox.getStyleClass().add("default-grid-pane");
        setUpVBox();

    }

    protected void setUpVBox()
    {
        getChildren().add(periodPanel);
    }

}
