package com.pap.view.pages;

import com.pap.consts.StageDefault;
import com.pap.database.user.LastLogin;
import com.pap.database.user.User;
import com.pap.session.UserSession;
import com.pap.view.alerts.AlertFactory;
import com.pap.view.stage.StylesheetInitializer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
public class CreateUserPage {
    private static final String DIALOG_TITLE = "User creation Failed";
    private static final String DIALOG_CONTENT = "User with given name already exists";

    @Value("classpath:/javafx/mainStage.fxml")
    private Resource mainStageResource;

    @Value("classpath:/javafx/loginPage.fxml")
    private Resource loginResource;

    private final UserSession userSession;

    private final ApplicationContext applicationContext;
    private final StylesheetInitializer stylesheetInitializer;
    private final StageDefault stageDefault;


    @FXML
    public VBox root;
    @FXML
    public TextField username;
    @FXML
    public TextField email;
    @FXML
    public PasswordField password;
    @FXML
    public Button goBack;
    @FXML
    public Button create;

    public void tryAddingUser(final ActionEvent event) throws IOException
    {
        if(!userSession.getUserRepository().existsUserByUsername(username.getText()))
        {
            final var newUser = User.builder()
                    .withUsername(username.getText())
                    .withEmail(email.getText())
                    .withPassword(password.getText())
                    .build();

            userSession.createNewUser(newUser);

            final var lastLogin = LastLogin.builder()
                    .withUsername(username.getText())
                    .withLastLoginTime(LocalDateTime.now())
                    .build();
            userSession.addUserSession(lastLogin);

            final var parent = getParentFromFXML(mainStageResource);
            final var newScene = new Scene(parent);
            var stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stylesheetInitializer.addFilesToScene(newScene);
            stage.setScene(newScene);
            stage.show();
        }
        else
        {
            AlertFactory.createErrorDialog(DIALOG_TITLE,DIALOG_CONTENT).show();
            log.warn("Username {} already exists",username.getText());
        }
    }

    public void goBackToLogin(final ActionEvent event) throws IOException
    {
        final var parent = getParentFromFXML(loginResource);
        final var newScene = new Scene(parent, stageDefault.getWidth(), stageDefault.getHeight());
        var stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stylesheetInitializer.addFilesToScene(newScene);
        stage.setScene(newScene);
        stage.show();
    }

    private Parent getParentFromFXML(final Resource resource) throws IOException
    {
        final var fxmlLoader = new FXMLLoader(resource.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        return fxmlLoader.load();
    }


    public void highlightGoBackButton(final MouseEvent event)
    {
        create.getStyleClass().clear();
        create.getStyleClass().add("createUserButton");
    }

    public void rollbackGoBackButton(final MouseEvent event)
    {
        create.getStyleClass().clear();
        create.getStyleClass().add("highlightCreateUserButton");
    }


    public void highlightCreateButton(final MouseEvent event)
    {
        goBack.getStyleClass().clear();
        goBack.getStyleClass().add("goBackButton");
    }

    public void rollbackCreateButton(final MouseEvent event)
    {
        goBack.getStyleClass().clear();
        goBack.getStyleClass().add("highlightGoBackButton");
    }
}
