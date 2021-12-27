package com.pap.view.stage;

import com.pap.consts.StageDefault;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StageChanger {

    private final ApplicationContext applicationContext;
    private final StylesheetInitializer stylesheetInitializer;
    private final StageDefault stageDefault;

    public void generateNewScene(final Resource resource, final ActionEvent event) throws IOException
    {
        final var parent = getParentFromFXML(resource);
        final var newScene = new Scene(parent, stageDefault.getWidth(), stageDefault.getHeight());
        var stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stylesheetInitializer.addFilesToScene(newScene);
        stage.setResizable(false);
        stage.setScene(newScene);
        stage.show();
    }

    private Parent getParentFromFXML(final Resource resource) throws IOException
    {
        final var fxmlLoader = new FXMLLoader(resource.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        return fxmlLoader.load();
    }
}
