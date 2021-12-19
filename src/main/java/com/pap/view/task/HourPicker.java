package com.pap.view.task;

import javafx.scene.control.TextField;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class HourPicker extends TextField {
    private final static String HOUR_FORMAT = "(2[0-3]|[01][0-9]):[0-5][0-9]";
    public HourPicker()
    {
        super(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    public Optional<String> getFormattedText()
    {
        if(getText().matches(HOUR_FORMAT))
        {
            getStyleClass().remove("hour-picker-bad");
            getStyleClass().add("hour-picker");
            return Optional.of(getText());
        }
        getStyleClass().remove("hour-picker");
        getStyleClass().add("hour-picker-bad");
        return Optional.empty();
    }
}
