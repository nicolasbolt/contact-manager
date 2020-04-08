package com.nicolasbolt.contacts;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    @FXML
    private TableView<Contact> contactTable;

    @FXML
    private TableColumn<Contact, String> firstNameCol;

    @FXML
    private TableColumn<Contact, String> lastNameCol;

    @FXML
    private TableColumn<Contact, String> phoneNumberCol;

    @FXML
    private TableColumn<Contact, String> notesCol;

    @FXML
    private GridPane mainGridPane;

    @FXML
    private ContextMenu tableContextMenu;

    public void initialize() {

        tableContextMenu = new ContextMenu();
        MenuItem editMenuItem = new MenuItem("Edit");
        MenuItem deleteMenuItem = new MenuItem("Delete");
        editMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Contact contact = contactTable.getSelectionModel().getSelectedItem();
                showEditContactDialog(contact);
            }
        });
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Contact contact = contactTable.getSelectionModel().getSelectedItem();
                deleteContact(contact);
            }
        });

        tableContextMenu.getItems().addAll(editMenuItem, deleteMenuItem);

        firstNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("phoneNumber"));
        notesCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("notes"));
        contactTable.setItems(ContactData.getInstance().getContacts());
        contactTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        contactTable.getSelectionModel().selectFirst();
        contactTable.setContextMenu(tableContextMenu);


    }

    @FXML
    public void showNewContactDialog() {
        Dialog<ButtonType> newContactDialog = new Dialog<>();
        newContactDialog.initOwner(mainGridPane.getScene().getWindow());
        newContactDialog.setTitle("Add Contact");
        newContactDialog.setHeaderText("Add A New Contact to The Contact List");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation((getClass().getResource("addContact.fxml")));
        try {
            newContactDialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        newContactDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        newContactDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = newContactDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            AddContactController newContactController = fxmlLoader.getController();
            Contact newContact = newContactController.processResults();
            ContactData.getInstance().saveContacts();

        }
    }

    public void handleDeleteAction() {
        Contact selectedItem = contactTable.getSelectionModel().getSelectedItem();
        deleteContact(selectedItem);
    }

    public void deleteContact(Contact contact) {
        if(!SettingsController.isDeleteWarningIsSelected()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Contact");
            alert.setHeaderText("Delete Contact: " + contact.getFirstName() + " " + contact.getLastName());
            alert.setContentText("Are You Sure You Want To Delete This Contact?  Press OK to Confirm.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ContactData.getInstance().deleteContact(contact);
                ContactData.getInstance().saveContacts();
            }
        } else {
            ContactData.getInstance().deleteContact(contact);
            ContactData.getInstance().saveContacts();
        }
    }

    @FXML
    public void handleEditAction() {
        Contact selectedItem = contactTable.getSelectionModel().getSelectedItem();
        showEditContactDialog(selectedItem);
    }

    public void showEditContactDialog(Contact contact) {
        Dialog<ButtonType> newContactDialog = new Dialog<>();
        newContactDialog.initOwner(mainGridPane.getScene().getWindow());
        newContactDialog.setTitle("Edit Contact");
        newContactDialog.setHeaderText("Edit A Contact In The Contact List");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation((getClass().getResource("addContact.fxml")));
        AddContactController newContactController;
        try {
            newContactDialog.getDialogPane().setContent(fxmlLoader.load());
            newContactController = fxmlLoader.getController();
            newContactController.setFields(contact);
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        newContactDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        newContactDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = newContactDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            newContactController = fxmlLoader.getController();
            Contact updatedItem = newContactController.updateResults(contact);
            contactTable.getSelectionModel().select(updatedItem);
            ContactData.getInstance().saveContacts();
        }
    }

    public void showXMLExportDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainGridPane.getScene().getWindow());
        dialog.setTitle("Export Contacts");
        dialog.setHeaderText("Export Contact List to XML File");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation((getClass().getResource("Export.fxml")));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ExportController exportController = fxmlLoader.getController();
            String filePath = ExportController.getFilePath();
            ContactData.getInstance().saveContacts(filePath);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export Successful");
            alert.setHeaderText("XML File Exported: " + filePath);
            alert.setContentText("Press OK to Return to the Previous Screen");
            Optional<ButtonType> exportResult = alert.showAndWait();


        }
    }

    public void showXMLImportDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainGridPane.getScene().getWindow());
        dialog.setTitle("Import Contacts");
        dialog.setHeaderText("Import Contact List From XML File");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation((getClass().getResource("Import.fxml")));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ImportController ImportController = fxmlLoader.getController();
            String filePath = ImportController.getFilePath();
            ContactData.getInstance().loadContacts(filePath);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Import Successful");
            alert.setHeaderText("XML File Imported: " + filePath);
            alert.setContentText("Press OK to Return to the Previous Screen");
            Optional<ButtonType> exportResult = alert.showAndWait();
            ContactData.getInstance().saveContacts();


        }
    }

    @FXML
    public void showSettingsDialog()  {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainGridPane.getScene().getWindow());
        dialog.setTitle("Contact Manager Settings");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation((getClass().getResource("Settings.fxml")));
        SettingsController settingsController = fxmlLoader.getController();
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.APPLY) {
        }
    }

    @FXML
    public void showDocumentationDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainGridPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Docs.fxml"));
        DocsController docsController = fxmlLoader.getController();
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Optional<ButtonType> result = dialog.showAndWait();
    }

}
