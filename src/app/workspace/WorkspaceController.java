package app.workspace;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class WorkspaceController {
    public Pane navBar;
    @FXML
    private BorderPane borderPane;

    // handler to load addProduct scene on button click action
    @FXML
    void loadAddProductScene(MouseEvent event) {
        loadPage("/app/addProduct/AddProduct.fxml", event);
    }

    // handler to load components scene on button click action
    @FXML
    void loadComponentsScene(MouseEvent event) {
        loadPage("/app/components/Components.fxml", event);
    }

    // handler to load systems scene on button click action
    @FXML
    void loadSystemsScene(MouseEvent event) {
        loadPage("/app/systems/Systems.fxml", event);
    }

    // handler to load home scene on button click action
    @FXML
    void loadHomeScene(MouseEvent event) {
        loadPage("/app/home/Home.fxml", event);
    }

    @FXML
    void initialize() {

        // load home scene by default
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(getClass().getResource("/app/home/Home.fxml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        borderPane.setCenter(rootNode);
    }

    // utility to switch active tabs
    void loadPage(String scene, MouseEvent event) {

        // remove active class from other navigation buttons
        for (Node btn : navBar.getChildren()) {
            btn.getStyleClass().remove("active");
        }

        // add active class to opened tab
        ((Node) event.getSource()).getStyleClass().add("active");

        // load required scene
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(scene));
        } catch (IOException ex) {
            Logger.getLogger(WorkspaceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderPane.setCenter(root);
    }
}