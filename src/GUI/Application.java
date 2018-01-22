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
    private SearchPanel searchPanel;
    private Label welcomeLabel;
    private Label notFoundLabel;
    private TextField searchField;
    private Button searchButton;
    private Button newCafeButton;

    public Application(Stage lastStage, Idatabase database){

        lastStage.hide();

        //Window - setup
        BorderPane borderPane = new BorderPane();
        appStage = new Stage();
        menuPanel = new MenuPanel(this, appStage, borderPane);
        appStage.setTitle("Aplikace káva - Hlavní obrazovka");
        Scene scene = new Scene(borderPane, 600, 300);
        appStage.setScene(scene);


        welcomeLabel = new Label();
        welcomeLabel.setText("Vítejte uživateli " + database.getSearchDatabase().getLoggedPerson().getUsername());
        notFoundLabel = new Label();
        notFoundLabel.setText("Nic podobného jsme nenalezli, zkuste zadat něco jiného");
        FlowPane infoPane = new FlowPane();
        infoPane.getChildren().addAll(welcomeLabel);
        infoPane.setAlignment(Pos.CENTER);

        //PANEL - vysledky hledani
        ScrollPane searchResults = new ScrollPane();
        searchResults.setFitToWidth(true);
        //PANEL - hledani field + button
        searchField = new TextField();
        searchButton = new Button();
        searchButton.setText("Vyhledat");


        //PANEL - button nova kavarna
        newCafeButton = new Button();
        newCafeButton.setText("Přidat kavárnu");
        HBox controlsPanel = new HBox(5);
        controlsPanel.getChildren().addAll(searchField, searchButton, newCafeButton);
        controlsPanel.setAlignment(Pos.TOP_CENTER);


        VBox vBox = new VBox();
        vBox.getChildren().addAll(controlsPanel, infoPane);

        searchPanel = new SearchPanel(appStage, database);
        //FUNCTION - search button
        searchButton.setOnAction(event -> {
            String text = searchField.getText();
            String sql = "SELECT * FROM sql11216990.cafe WHERE name LIKE '%" + text + "%'";
            boolean databaseOperation = database.getSearchDatabase().databaseOperation("SEARCH", sql);
            if(databaseOperation){
                vBox.getChildren().clear();
                searchResults.setContent(searchPanel);
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
