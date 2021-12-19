package com.pap.view.task.buttons.factory;

import com.pap.view.task.buttons.custom.ActiveButton;
import com.pap.view.task.buttons.custom.HighlightableButton;

public class ButtonFactory {

    public static ActiveButton createActiveButtonTurnedOn(final ButtonTypes type, final String activeStyle)
    {
        final var button = createActiveButton(type, activeStyle);
        button.getStyleClass().clear();
        button.getStyleClass().add(activeStyle);
        return button;
    }
    public static ActiveButton createActiveButton(final ButtonTypes type, final String activeStyle)
    {
        final var button = new ActiveButton(type,activeStyle);
        button.setText(type.getText());
        return button;
    }

    public static HighlightableButton createButtonWithStyle(final ButtonTypes type)
    {
        final var button = new HighlightableButton(type.getHighlightedStyle(),type.getNormalStyle());
        button.setText(type.getText());
        return button;
    }
}
