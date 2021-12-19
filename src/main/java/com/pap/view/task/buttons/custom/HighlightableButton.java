package com.pap.view.task.buttons.custom;


import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class HighlightableButton extends Button {
    private final String highlightedStyle;
    private final String normalStyle;
    public HighlightableButton(final String highlightedStyle, final String normalStyle)
    {
        super();
        this.highlightedStyle = highlightedStyle;
        this.normalStyle = normalStyle;
        this.setOnMouseEntered(this::highlightLoginButton);
        this.setOnMouseExited(this::rollbackLoginButton);
        getStyleClass().add(normalStyle);
    }

    void highlightLoginButton(final MouseEvent event)
    {
        getStyleClass().remove(normalStyle);
        getStyleClass().add(highlightedStyle);
    }

    void rollbackLoginButton(final MouseEvent event)
    {
        getStyleClass().remove(highlightedStyle);
        getStyleClass().add(normalStyle);
    }
}
