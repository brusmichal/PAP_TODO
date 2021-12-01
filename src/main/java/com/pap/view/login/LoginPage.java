package com.pap.view.login;

import com.pap.database.user.repository.UserRepository;
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
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginPage {

    @Value("classpath:/javafx/mainStage.fxml")
    private Resource mainStageResource;

    private final UserRepository userRepository;
    private final ApplicationContext applicationContext;
    private final StylesheetInitializer stylesheetInitializer;

    @FXML
    public TextField usernameField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public Button login;

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
        }
    }

    private boolean checkIfUserIsValid(String username, String password)
    {
        return true;
    }

    private Parent getParentFromFXML() throws IOException
    {
        final var fxmlLoader = new FXMLLoader(mainStageResource.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        return fxmlLoader.load();
    }
}
