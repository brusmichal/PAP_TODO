package com.pap;

import com.pap.view.TaskApplication;
import javafx.application.Application;
import javafx.scene.control.Slider;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableConfigurationProperties
@SpringBootApplication
public class Pap21zZ25Application {

    public static void main(String[] args)
    {
        Application.launch(TaskApplication.class, args);
    }

    @Bean
    public Slider timeSlider()
    {
        final var slider = new Slider(0, 60, 15);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(10f);
        slider.setBlockIncrement(1f);
        slider.getStyleClass().add("slider");
        return slider;
    }
}
