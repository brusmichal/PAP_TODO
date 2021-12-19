package com.pap.view;

import com.pap.Pap21zZ25Application;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
public class TaskApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init()
    {
        applicationContext = new SpringApplicationBuilder(Pap21zZ25Application.class).run();
    }

    @Override
    public void start(final Stage primaryStage)
    {
        applicationContext.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop()
    {
        log.info("Application closure");
        applicationContext.close();
        Platform.exit();
    }

    public static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(final Stage primaryStage)
        {
            super(primaryStage);
        }

        public Stage getStage()
        {
            return (Stage) getSource();
        }
    }
}
