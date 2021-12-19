package com.pap.view.task.buttons.custom;


import com.pap.view.task.buttons.factory.ButtonTypes;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.Setter;

public class ActiveButton extends HighlightableButton{
    private final String activeStyle;
    @Getter
    @Setter
    private boolean active = false;

    public ActiveButton(final ButtonTypes type, final String activeStyle)
    {
        super(type.getHighlightedStyle(), type.getNormalStyle());
        this.activeStyle = activeStyle;
    }

    @Override
    void rollbackLoginButton(final MouseEvent event)
    {
        if(active)
        {
            getStyleClass().clear();
            getStyleClass().add(activeStyle);
        }
        else
        {
            super.rollbackLoginButton(event);
        }
    }
}
