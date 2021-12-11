package com.pap.view.pages;

import com.pap.consts.StageDefault;
import com.pap.database.user.LastLogin;
import com.pap.session.UserSession;
import com.pap.view.stage.StylesheetInitializer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginPage {

    @Value("classpath:/javafx/mainStage.fxml")
    private Resource mainStageResource;

    @Value("classpath:/javafx/create.fxml")
    private Resource createUserResource;

    private final UserSession userSession;

    private final ApplicationContext applicationContext;
    private final StylesheetInitializer stylesheetInitializer;
    private final StageDefault stageDefault;

    @FXML
    public TextField usernameField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public Button login;

    @FXML
    public VBox loginPage;

    public void tryToLogIn(final ActionEvent event) throws IOException
    {
        if(checkIfUserIsValid(usernameField.getText(),passwordField.getText()))
        {
            generateNewScene(mainStageResource,event);
            createUserSession();
        }
        else
        {
            final var label = new Label("INCORRECT PASSWORD");
            label.getStyleClass().add("incorrect-username-or-password");
            loginPage.getChildren().add(label);

            log.warn("Username {} failed to login",usernameField.getText());
        }
    }

    public void createUser(final ActionEvent event) throws IOException
    {
        generateNewScene(createUserResource,event);
    }

    private void generateNewScene(final Resource resource, final ActionEvent event) throws IOException
    {
        final var parent = getParentFromFXML(resource);
        final var newScene = new Scene(parent, stageDefault.getWidth(), stageDefault.getHeight());
        var stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stylesheetInitializer.addFilesToScene(newScene);
        stage.setScene(newScene);
        stage.show();
    }
    private boolean checkIfUserIsValid(String username, String password)
    {
        return userSession.getUserRepository().existsUserByUsernameAndPassword(username,password);
    }

    private Parent getParentFromFXML(final Resource resource) throws IOException
    {
        final var fxmlLoader = new FXMLLoader(resource.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        return fxmlLoader.load();
    }

    private void createUserSession()
    {
        final var lastLogin = LastLogin.builder()
                .withUsername(usernameField.getText())
                .withLastLoginTime(LocalDateTime.now())
                .build();
        userSession.addUserSession(lastLogin);
        log.info("Successful login to application by user {}", usernameField.getText());

    }
}