package com.nicolasbolt.contacts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("Contact Manager");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        String currentUsersHomeDir = System.getProperty("user.home");
        new File(currentUsersHomeDir + "/Library/Application Support/ContactManager/").mkdirs();
        ContactData.getInstance().loadContacts();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
