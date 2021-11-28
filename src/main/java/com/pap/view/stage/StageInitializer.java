package com.pap.view.stage;


import com.pap.consts.StageDefault;
import com.pap.view.TaskApplication.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Initializes stage component and all stylesheets.
 */
@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Value("classpath:/javafx/mainStage.fxml")
    private Resource mainStageResource;

    @Value("classpath:/images/icon.png")
    private Resource icon;

    private final StageDefault stageDefault;
    private final ApplicationContext applicationContext;
    private final StylesheetInitializer stylesheetInitializer;

    @Autowired
    public StageInitializer(final StageDefault stageDefault, final ApplicationContext applicationContext, final StylesheetInitializer stylesheetInitializer)
    {
        this.stageDefault = stageDefault;
        this.applicationContext = applicationContext;
        this.stylesheetInitializer = stylesheetInitializer;
    }

    @Override
    public void onApplicationEvent(final StageReadyEvent event)
    {
        try
        {
            final var stage = event.getStage();
            final Parent parent = getParentFromFXML();
            final var scene = new Scene(parent, stageDefault.getWidth(), stageDefault.getHeight());
            stylesheetInitializer.addFilesToScene(scene);

            setUpStage(stage, scene);
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }

    private Parent getParentFromFXML() throws IOException
    {
        final var fxmlLoader = new FXMLLoader(mainStageResource.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        return fxmlLoader.load();
    }

    private void setUpStage(final Stage stage, final Scene scene) throws IOException
    {
        stage.getIcons().add(new Image(icon.getInputStream()));
        stage.setTitle(stageDefault.getTitle());
        stage.setScene(scene);
        stage.show();
    }
}
