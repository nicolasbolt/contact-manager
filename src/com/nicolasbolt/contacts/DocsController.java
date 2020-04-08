package com.nicolasbolt.contacts;

import javafx.fxml.FXML;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DocsController {

    @FXML
    public void handleLinkClick() {
        try  {
            Desktop.getDesktop().browse(new URI("https://github.com/nicolasbolt/contact-manager"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
