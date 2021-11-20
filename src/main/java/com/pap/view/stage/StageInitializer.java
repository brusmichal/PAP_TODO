package com.pap.view.stage;


import com.pap.consts.StageDefault;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import com.pap.view.TaskApplication.StageReadyEvent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Value("classpath:/javafx/mainStage.fxml")
    private Resource mainStageResource;

    private final StageDefault stageDefault;
    private final ApplicationContext applicationContext;

    @Autowired
    public StageInitializer(final StageDefault stageDefault, final ApplicationContext applicationContext)
    {
        this.stageDefault = stageDefault;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(final StageReadyEvent event)
    {
        try
        {
            final var fxmlLoader = new FXMLLoader(mainStageResource.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            final Parent parent = fxmlLoader.load();
            final var stage = event.getStage();
            stage.setTitle(stageDefault.getTitle());
            stage.setScene(new Scene(parent, stageDefault.getWidth(), stageDefault.getHeight()));
            stage.show();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
}
