package com.nicolasbolt.contacts;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddContactController {

    @FXML
    private TextField fNameField;

    @FXML
    private TextField lNameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextArea notesField;

    public Contact processResults() {
        String firstName = fNameField.getText().trim();
        String lastName = lNameField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String notes = notesField.getText().trim();

        Contact newContact = new Contact(firstName, lastName, phoneNumber, notes);
        ContactData.getInstance().addContact(newContact);
        return newContact;

    }

    public void setFields(Contact contact) {
        fNameField.setText(contact.getFirstName());
        lNameField.setText(contact.getLastName());
        phoneNumberField.setText(contact.getPhoneNumber());
        notesField.setText(contact.getNotes());
    }

    public Contact updateResults(Contact contact) {
        String firstName = fNameField.getText();
        String lastName = lNameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String notes = notesField.getText();

        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setPhoneNumber(phoneNumber);
        contact.setNotes(notes);

        return contact;
    }
}
