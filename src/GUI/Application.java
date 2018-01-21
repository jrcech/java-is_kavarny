package GUI;

import interfaces.Idatabase;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Application {

    private Stage appStage;
    private MenuPanel menuPanel;

    public Application(Stage lastStage, Idatabase database){

        lastStage.hide();

        //Window - setup
        BorderPane borderPane = new BorderPane();
        appStage = new Stage();
        menuPanel = new MenuPanel(this, appStage, borderPane);
        appStage.setTitle("Aplikace káva - Hlavní obrazovka");
        Scene scene = new Scene(borderPane, 600, 300);
        appStage.setScene(scene);


        Label welcome = new Label();
        welcome.setText("Vítejte ");
        FlowPane welcomePane = new FlowPane();
        welcomePane.getChildren().addAll(welcome);
        welcomePane.setAlignment(Pos.CENTER);
        // PANEL - vysledky hledani
        ScrollPane searchResults = new ScrollPane();
        searchResults.setFitToWidth(true);

        // PANEL - hledani + nova kavarna
        TextField searchField = new TextField();
        Button searchButton = new Button();
        searchButton.setText("Vyhledat");
        Button newCafeButton = new Button();
        newCafeButton.setText("Přidat kavárnu");
        HBox controlsPanel = new HBox(5);
        controlsPanel.getChildren().addAll(searchField, searchButton, newCafeButton);
        controlsPanel.setAlignment(Pos.TOP_CENTER);


        VBox vBox = new VBox();
        vBox.getChildren().addAll(controlsPanel, welcomePane);
        searchButton.setOnAction(event -> {
            vBox.getChildren().addAll(searchResults);
        });







        borderPane.setTop(menuPanel);
        borderPane.setCenter(vBox);
        appStage.show();

    }

}
