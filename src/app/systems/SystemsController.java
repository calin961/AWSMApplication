//package app.systems;
//
//import app.services.APIHandler;
//import app.services.prototype.Product;
//import app.systems.update.UpdateController;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.Pane;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class SystemsController extends Product {
//
//    public Pane component_wrapper;
//    @FXML
//    private Label providerLabel;
//    @FXML
//    private Label nameLabel;
//    @FXML
//    private Label priceLabel;
//    @FXML
//    private Label commentsLabel;
//    @FXML
//    private Pane imagePane;
//
//    @FXML
//    private Button deleteBtn;
//    @FXML
//    private Button updateBtn;
//
//    public SystemsController(int id, int categoryId, String name, int amount, int price, Boolean paid, String date, String image) {
//        super(id, categoryId, name, amount, price, paid, date, image);
//    }
//
//    @FXML
//    public Pane initialize() {
//        nameLabel.setText(name);
//        imagePane.setStyle("-fx-background-image: url(" + image + ")");
//
//        deleteBtn.setOnAction(event -> {
//            try {
//                APIHandler.makeRequest("DELETE", "systems", this.getDeleteJSON());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//        updateBtn.setOnAction(event -> {
//            UpdateController updateComponent =
//                    new UpdateController(id, categoryId, name, amount, price, paid, date, image);
//            FXMLLoader loader = new FXMLLoader(
//                    getClass().getResource("/app/systems/update/Update.fxml")
//            );
//            loader.setController(updateComponent);
//            Stage stage = new Stage();
//            Parent dialog = null;
//            try {
//                dialog = loader.load();
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//            assert dialog != null;
//            stage.setScene(new Scene(dialog));
//            stage.setTitle("Update system");
//            stage.initModality(Modality.WINDOW_MODAL);
//            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
//            stage.show();
//        });
//        return component_wrapper;
//    }
//}
package app.systems;

import app.services.ProductsLists;
import app.systems.card.SystemsCardController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import org.json.JSONException;

import java.io.*;
import java.util.*;

public class SystemsController {

    @FXML
    private AnchorPane wrapper;
    @FXML
    private ScrollPane scroll;
    @FXML
    private TextField searchBar;
    @FXML
    private ComboBox categoryBox;
    @FXML
    private Button showAll;


    Set<String> categories = new HashSet<String>();

    @FXML
    public void appendTemplate(SystemsCardController systems,
                               int layoutX, int layoutY) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/systems/card/SystemsCard.fxml"));
        loader.setController(systems);
        Pane systemsCard = loader.load();
        systemsCard.setLayoutX(layoutX);
        systemsCard.setLayoutY(layoutY);

        wrapper.getChildren().add(systemsCard);
        scroll.setContent(wrapper);
    }

    @FXML
    private void search(ActionEvent event) throws IOException, JSONException {
        renderView(((TextField) event.getSource()).getText());
    }

    @FXML
    public void filter(ActionEvent event) throws IOException, JSONException {
        renderView(categoryBox.getValue().toString());
    }

    @FXML
    void renderView(String filter) throws IOException, JSONException {
        wrapper.getChildren().clear();
        int layoutY = 30;

        int systemsAmount = ProductsLists.getSystemsAmount();
        int occurrences = 0;
        for (int i = 0; i < systemsAmount; i++) {

            Systems systems = ProductsLists.getSystems(i);
            categories.add(String.valueOf(systems.categoryName));

            int layoutX = 50;
            if (occurrences % 3 == 1) {
                layoutX = 270;
            } else if (occurrences % 3 == 2) {
                layoutX = 480;
            }
            if (filter.equals("none")
                    || filter.equals(systems.categoryName)
                    || systems.name.toLowerCase().contains(filter.toLowerCase())) {

                appendTemplate(new SystemsCardController(systems, categories), layoutX, layoutY);
                layoutY += (occurrences + 1) % 3 != 0 ? 0 : 270;
                occurrences++;
            }
        }
    }

    @FXML
    void renderAll() throws IOException, JSONException {
        renderView("none");
    }

    @FXML
    public void initialize() throws IOException, JSONException {
        renderView("none");

        categoryBox.setItems(FXCollections.observableArrayList(categories));
        categoryBox.getSelectionModel().selectFirst();
    }
}
