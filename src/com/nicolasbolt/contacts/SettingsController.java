package com.nicolasbolt.contacts;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class SettingsController {
    @FXML
    private CheckBox deleteWarning;

    private static boolean deleteWarningIsSelected;

    @FXML
    public void handleDeleteWarning() {
        if(deleteWarning.isSelected()) {
            setDeleteWarningIsSelected(true);
        } else {
            setDeleteWarningIsSelected(false);
        }
    }

    public CheckBox getDeleteWarning() {
        return deleteWarning;
    }

    public static boolean isDeleteWarningIsSelected() {
        return deleteWarningIsSelected;
    }

    public static void setDeleteWarningIsSelected(boolean bool) {
            deleteWarningIsSelected = bool;
    }
}

