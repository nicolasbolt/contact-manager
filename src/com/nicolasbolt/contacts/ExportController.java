package com.nicolasbolt.contacts;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Paths;


public class ExportController {

    @FXML
    private TextField fileName;

    @FXML
    private GridPane saveDialog;

    private static String filePath;

    public String getFileName() {
        String fn = fileName.getText();
        return fn;
    }

    public static String getFilePath() {
        return filePath;
    }

    @FXML
    public void handleExportClick() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML", "*.xml")
        );
        File file = chooser.showSaveDialog(saveDialog.getScene().getWindow());

        if(file != null) {
            filePath = file.getPath();
        } else {
            filePath = Paths.get(".").toAbsolutePath().normalize().toString();
        }
    }
}
