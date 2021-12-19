package com.pap;

import com.pap.view.TaskApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class Pap21zZ25Application {

    public static void main(String[] args)
    {
        Application.launch(TaskApplication.class, args);
    }

}
