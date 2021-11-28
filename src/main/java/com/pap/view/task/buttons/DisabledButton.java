package com.pap.view.task.buttons;

import javafx.scene.control.Button;

public class DisabledButton extends Button {
    public DisabledButton()
    {
        super();
        this.setOnMouseEntered((event) -> this.setDisabled(false));
        this.setOnMouseExited((event) -> this.setDisabled(true));
    }
}
