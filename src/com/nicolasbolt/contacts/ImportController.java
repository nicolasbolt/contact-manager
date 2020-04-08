package com.nicolasbolt.contacts;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Paths;

public class ImportController {

    @FXML
    private String filePath;

    @FXML
    private GridPane openDialog;

    public String getFilePath() {
        return filePath;
    }

    @FXML
    public void handleImportClick() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML", "*.xml")
        );
        File file = chooser.showOpenDialog(openDialog.getScene().getWindow());
        filePath = file.getPath();
    }
}
