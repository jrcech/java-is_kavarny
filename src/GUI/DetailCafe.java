package GUI;

import interfaces.Idatabase;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Cafe;

public class DetailCafe {

    private Stage detailStage;
    private Idatabase database;
    private RatingPanel ratingPanel;
    private Label nameLabel;
    private Label addressLabel;
    private Label regionLabel;
    private Label shortDescLabel;
    private Label descLabel;
    private Label offerLabel;
    private Label coffeeBrandLabel;
    private Label eventLabel;
    private TextField nameField;
    private TextField addressField;
    private TextField regionField;
    private TextField shortDescField;
    private TextArea descArea;
    private TextField offerField;
    private TextField coffeeBrandField;
    private TextField eventField;

    private Button editButton;
    private Button cancelButton;

    public DetailCafe(Stage lastStage, Idatabase database, int idCafe){

        this.database = database;
        ratingPanel = new RatingPanel(database, idCafe);
        //Titulek
        Text title = new Text();
        title.setText("Detailní údaje kavárny");

        //Form - Nazev
        nameLabel = new Label();
        nameLabel.setText("Název:");
        nameField = new TextField();
        nameField.setEditable(false);

        //Form - Adresa
        addressLabel = new Label();
        addressLabel.setText("Adresa:");
        addressField = new TextField();
        addressField.setEditable(false);

        //Form - Kraje
        regionLabel = new Label();
        regionLabel.setText("Kraj:");
        regionField = new TextField();
        regionField.setEditable(false);

        //Form - Popis
        shortDescLabel = new Label();
        shortDescLabel.setText("Krátký popis:");
        shortDescField = new TextField();
        shortDescField.setEditable(false);

        //Form - Popis
        descLabel = new Label();
        descLabel.setText("Popis:");
        descLabel.setWrapText(true);
        descArea = new TextArea();
        descArea.setEditable(false);
        descArea.setPrefRowCount(4);
        descArea.setWrapText(true);


        //Form - Nabidka
        offerLabel = new Label();
        offerLabel.setText("Nabídka:");
        offerField = new TextField();
        offerField.setEditable(false);

        //Form - Znacka kavy
        coffeeBrandLabel = new Label();
        coffeeBrandLabel.setText("Značka kávy: ");
        coffeeBrandField = new TextField();
        coffeeBrandField.setEditable(false);

        //Form - Udalost
        eventLabel = new Label();
        eventLabel.setText("Událost:");
        eventLabel.setWrapText(true);
        eventField = new TextField();
        eventField.setEditable(false);

        //LOOP - Priradi hodnoty do formulare
        for (Cafe cafe : database.getSearchDatabase().getCafe()) {
            if (idCafe == cafe.getId()) {
                nameField.setText(cafe.getName());
                addressField.setText(cafe.getAddress());
                regionField.setText(cafe.getRegion());
                shortDescField.setText(cafe.getShortDescription());
                descArea.setText(cafe.getDescription());
                coffeeBrandField.setText(cafe.getCoffeeBrand());
                eventField.setText(cafe.getEvent());
                offerField.setText(cafe.getSpecialOffer());
            }
        }

        //Tlacitko - submit
        editButton = new Button();
        editButton.setText("Upravit");
        editButton.setOnAction(event -> {
            EditCafe editCafe = new EditCafe(detailStage, database, idCafe);
        });

        //Tlacitko - cancel
        cancelButton = new Button();
        cancelButton.setText("Zpět");
        cancelButton.setOnAction(event -> {
            detailStage.hide();
            lastStage.show();
        });


        // TilePane - spojeni tlacitek
        HBox boxButtons = new HBox(5);
        boxButtons.getChildren().addAll(editButton, cancelButton);
        boxButtons.setAlignment(Pos.BASELINE_RIGHT);

        //GridPane - rozlozeni formulare s daty
        GridPane detailPane = new GridPane();
        detailPane.setAlignment(Pos.CENTER);
        detailPane.setHgap(10);
        detailPane.setVgap(10);
        detailPane.add(title,0,0, 2,1);
        detailPane.add(nameLabel,0,1);
        detailPane.add(nameField,1,1);
        detailPane.add(addressLabel,0,2);
        detailPane.add(addressField,1,2);
        detailPane.add(regionLabel,0,3);
        detailPane.add(regionField,1,3);
        detailPane.add(shortDescLabel,0,4);
        detailPane.add(shortDescField,1,4);
        detailPane.add(descLabel,0,5);
        detailPane.add(descArea,1,5);
        detailPane.add(coffeeBrandLabel,0,6);
        detailPane.add(coffeeBrandField,1,6);
        detailPane.add(eventLabel,0,7);
        detailPane.add(eventField,1,7);
        detailPane.add(offerLabel,0,8);
        detailPane.add(offerField,1,8);
        detailPane.add(boxButtons,1,9);

        ScrollPane ratingPane = new ScrollPane();
        ratingPane.setContent(ratingPanel);

        GridPane gridPane = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(60);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(40);
        gridPane.getColumnConstraints().addAll(col1,col2);
        detailPane.setAlignment(Pos.CENTER);
        gridPane.add(detailPane,0,0);
        gridPane.add(ratingPane,1,0);


        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);


        detailStage = new Stage();
        Scene scene = new Scene(borderPane, 600, 500);
        detailStage.setTitle("Aplikace káva - Detail Kavárny");
        detailStage.setScene(scene);
        detailStage.show();
        detailStage.setResizable(false);
    }
}
