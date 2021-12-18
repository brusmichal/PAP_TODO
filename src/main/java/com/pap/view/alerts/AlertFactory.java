package com.pap.view.alerts;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class AlertFactory {

    private final static String APPLICATION_ICON = "/images/icon.png";

    public static Alert createErrorDialog(final String title, final String content)
    {
        final var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setGraphic(null);
        alert.setHeaderText(null);
        alert.setContentText(content);
        final var stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Objects.requireNonNull(alert.getClass().getResource(APPLICATION_ICON)).toString()));
        return alert;
    }
}
