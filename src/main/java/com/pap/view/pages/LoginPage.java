package com.pap.view.pages;

import com.pap.database.user.LastLogin;
import com.pap.database.user.repository.LastLoginRepository;
import com.pap.database.user.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final LastLoginRepository lastLoginRepository;

    private final ApplicationContext applicationContext;
    private final StylesheetInitializer stylesheetInitializer;

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
            final var parent = getParentFromFXML();
            final var newScene = new Scene(parent);
            var stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stylesheetInitializer.addFilesToScene(newScene);
            stage.setScene(newScene);
            stage.show();
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

    private boolean checkIfUserIsValid(String username, String password)
    {
        return userRepository.existsUserByUsernameAndPassword(username,password);
    }

    private Parent getParentFromFXML() throws IOException
    {
        final var fxmlLoader = new FXMLLoader(mainStageResource.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        return fxmlLoader.load();
    }

    private void createUserSession()
    {
        final var userSession = LastLogin.builder()
                .withUsername(usernameField.getText())
                .withLastLoginTime(LocalDateTime.now())
                .build();
        lastLoginRepository.insert(userSession);
        log.info("Successful login to application by user {}", usernameField.getText());

    }
}
