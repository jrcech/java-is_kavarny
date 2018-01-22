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
import logic.Database;

public class EditCafe {

    private Stage editStage;
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
    private TextField shortDescField;
    private TextField descField;
    private TextField offerField;
    private TextField coffeeBrandField;
    private TextField eventField;

    private Button submitButton;
    private Button cancelButton;

    public EditCafe(Stage lastStage, Idatabase database, int idCafe){
        this.database = database;
        lastStage.hide();

        //Titulek
        Text title = new Text();
        title.setText("Vyplňte údaje kavárny");

        //Form - Nazev
        nameLabel = new Label();
        nameLabel.setText("Název:");
        nameField = new TextField();

        //Form - Adresa
        addressLabel = new Label();
        addressLabel.setText("Adresa:");
        addressField = new TextField();

        //Form - Kraje
        regionLabel = new Label();
        regionLabel.setText("Kraj:");
        ComboBox regionBox = new ComboBox();
        regionBox.getItems().addAll("Praha", "Středočeský Kraj", "Jihočeský Kraj", "Plzeňský kraj", "Karlovarský kraj", "Ústecký kraj", "Liberecký kraj", "Královehradecký kraj", "Pardubický kraj", "kraj Vysočina", "Jihomoravský kraj", "Olomoucký kraj", "Moravskoslezský kraj", "Zlínský kraj");

        //Form - Popis
        shortDescLabel = new Label();
        shortDescLabel.setText("Krátký popis:");
        shortDescField = new TextField();

        //Form - Popis
        descLabel = new Label();
        descLabel.setText("Popis:");
        descField = new TextField();
        descLabel.setWrapText(true);

        //Form - Nabidka
        offerLabel = new Label();
        offerLabel.setText("Nabídka:");
        offerField = new TextField();

        //Form - Znacka kavy
        coffeeBrandLabel = new Label();
        coffeeBrandLabel.setText("Značka kávy: ");
        coffeeBrandField = new TextField();

        //Form - Udalost
        eventLabel = new Label();
        eventLabel.setText("Událost:");
        eventField = new TextField();
        eventLabel.setWrapText(true);

        //Pokud neni kavarna nova (tzn nema id 99999999), vyplni se udaje z datavaze
        if (idCafe != 99999999) {
            for (Cafe cafe : database.getSearchDatabase().getCafe()) {
                if (idCafe == cafe.getId()) {
                    nameField.setText(cafe.getName());
                    addressField.setText(cafe.getAddress());
                    regionBox.setValue(cafe.getRegion());
                    shortDescField.setText(cafe.getShortDescription());
                    descField.setText(cafe.getDescription());
                    coffeeBrandField.setText(cafe.getCoffeeBrand());
                    eventField.setText(cafe.getEvent());
                    offerField.setText(cafe.getSpecialOffer());
                }
            }
        }

        //Tlacitko - submit
        submitButton = new Button();
        submitButton.setText("Potvrdit");
        submitButton.setOnAction(event -> {
            Application app = new Application(editStage, database);
        });

        //Tlacitko - cancel
        cancelButton = new Button();
        cancelButton.setText("Zpět");
        cancelButton.setOnAction(event -> {
            editStage.hide();
            lastStage.show();
        });


        // TilePane - spojeni tlacitek
        HBox boxButtons = new HBox(5);
        boxButtons.getChildren().addAll(submitButton, cancelButton);
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
        gridPane.add(regionBox,1,3);
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

        //Window - setup
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane, 450, 400);

        editStage = new Stage();
        editStage.setTitle("Aplikace káva - Přihlášení");
        editStage.setScene(scene);
        editStage.show();
    }
}
