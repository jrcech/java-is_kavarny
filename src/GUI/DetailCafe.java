package GUI;

import interfaces.Idatabase;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Cafe;

public class DetailCafe {

    private Stage detailStage;
    private Idatabase database;
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
    private TextField descField;
    private TextField offerField;
    private TextField coffeeBrandField;
    private TextField eventField;

    private Button editButton;
    private Button cancelButton;

    public DetailCafe(Stage lastStage, Idatabase database, int idCafe){

        this.database = database;

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
        descField = new TextField();
        descField.setEditable(false);


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
                descField.setText(cafe.getDescription());
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

        //GridPane - rozlozeni formulare
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(title,0,0, 2,1);
        gridPane.add(nameLabel,0,1);
        gridPane.add(nameField,1,1);
        gridPane.add(addressLabel,0,2);
        gridPane.add(addressField,1,2);
        gridPane.add(regionLabel,0,3);
        gridPane.add(regionField,1,3);
        gridPane.add(shortDescLabel,0,4);
        gridPane.add(shortDescField,1,4);
        gridPane.add(descLabel,0,5);
        gridPane.add(descField,1,5);
        gridPane.add(coffeeBrandLabel,0,6);
        gridPane.add(coffeeBrandField,1,6);
        gridPane.add(eventLabel,0,7);
        gridPane.add(eventField,1,7);
        gridPane.add(offerLabel,0,8);
        gridPane.add(offerField,1,8);
        gridPane.add(boxButtons,1,9);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);

        detailStage = new Stage();
        Scene scene = new Scene(borderPane, 400, 500);
        detailStage.setTitle("Aplikace káva - Detail Kavárny");
        detailStage.setScene(scene);
        detailStage.show();
        detailStage.setResizable(false);
    }
}
