package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import logic.Database;

public class Application {

    private Stage appStage;
    private MenuPanel menuPanel;
    private SearchPanel searchPanel;
    private Label welcomeLabel;
    private Label notFoundLabel;
    private Label foundLabel;
    private TextField searchField;
    private Button searchButton;
    private Button newCafeButton;

    public Application(Stage lastStage, Database database){

        lastStage.hide();

        //Window - setup
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("borderPane");
        appStage = new Stage();
        menuPanel = new MenuPanel(this, appStage, borderPane);
        appStage.setTitle("Aplikace káva - Hlavní obrazovka");
        Scene scene = new Scene(borderPane, 600, 720);
        scene.getStylesheets().add("styles/styles.css");
        appStage.setScene(scene);


        welcomeLabel = new Label();
        welcomeLabel.setText("Vítejte uživateli " + database.getLoggedPerson().getUsername());
        welcomeLabel.getStyleClass().add("welcomeLabel");
        notFoundLabel = new Label();
        notFoundLabel.setText("Nic podobného jsme nenalezli, zkuste zadat něco jiného");
        notFoundLabel.getStyleClass().add("notFoundLabel");
        foundLabel = new Label();
        foundLabel.setText("Seznam nalezených kaváren");
        foundLabel.getStyleClass().add("foundLabel");

        FlowPane infoPane = new FlowPane();
        infoPane.getStyleClass().add("infoPane");
        infoPane.getChildren().addAll(welcomeLabel);
        infoPane.setAlignment(Pos.CENTER);

        //PANEL - vysledky hledani
        ScrollPane searchResults = new ScrollPane();
        searchResults.setStyle("-fx-background-color:transparent;");
        searchResults.getStyleClass().add("searchResults");
        searchResults.setFitToWidth(true);

        //PANEL - hledani field + button
        searchField = new TextField();
        searchField.getStyleClass().add("searchField");
        searchButton = new Button();
        searchButton.setText("Vyhledat");


        //PANEL - button nova kavarna
        newCafeButton = new Button();
        newCafeButton.getStyleClass().add("blueButton");
        newCafeButton.setText("Přidat kavárnu");
        newCafeButton.setOnAction(event -> {
            EditCafe editCafe = new EditCafe(appStage, database, 99999999);
        });

        //CONTROLS PANEL
        HBox controlsPanel = new HBox(10);
        controlsPanel.getStyleClass().add("searchControlsPanel");
        controlsPanel.setHgrow(searchField, Priority.ALWAYS);
        controlsPanel.setAlignment(Pos.TOP_CENTER);
        if (!database.getLoggedPerson().isAdmin()) {
            controlsPanel.getChildren().addAll(searchField, searchButton);
        } else {
            controlsPanel.getChildren().addAll(searchField, searchButton, newCafeButton);
        }


        VBox vBox = new VBox();
        vBox.getChildren().addAll(controlsPanel, infoPane);

        searchPanel = new SearchPanel(appStage, database);
        //FUNCTION - search button
        searchButton.setOnAction(event -> {
            String text = searchField.getText();
            String sql = "SELECT * FROM sql11216990.cafe WHERE name LIKE '%" + text + "%'";
            boolean databaseOperation = database.operate("SEARCH", sql);
            if(databaseOperation){
                vBox.getChildren().clear();
                searchResults.setContent(searchPanel);
                infoPane.getChildren().clear();
                infoPane.getChildren().addAll(foundLabel);
                vBox.getChildren().addAll(controlsPanel, infoPane, searchResults);
            }
            else {
                infoPane.getChildren().clear();
                infoPane.getChildren().addAll(notFoundLabel);
            }

        });


        borderPane.setTop(menuPanel);
        borderPane.setCenter(vBox);
        appStage.show();

    }

}
