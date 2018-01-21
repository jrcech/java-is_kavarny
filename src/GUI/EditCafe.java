package GUI;

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

public class EditCafe {

    private Stage editStage;

    public EditCafe(Stage lastStage){
        lastStage.hide();

        //Titulek
        Text title = new Text();
        title.setText("Vyplňte údaje kavárny");

        //Form - Nazev
        Label nameLabel = new Label();
        nameLabel.setText("Název:");
        TextField nameField = new TextField();

        //Form - Adresa
        Label addressLabel = new Label();
        addressLabel.setText("Adresa:");
        TextField addressField = new TextField();

        //Form - Kraje
        Label regionLabel = new Label();
        regionLabel.setText("Kraj:");
        ComboBox regionBox = new ComboBox();
        regionBox.getItems().addAll("Praha", "Středočeský Kraj", "Jihočeský Kraj", "Plzeňský kraj", "Karlovarský kraj", "Ústecký kraj", "Liberecký kraj", "Královehradecký kraj", "Pardubický kraj", "kraj Vysočina", "Jihomoravský kraj", "Olomoucký kraj", "Moravskoslezský kraj", "Zlínský kraj");


        //Form - Popis
        Label descLabel = new Label();
        descLabel.setText("Popis:");
        TextField descField = new TextField();


        //Form - Nabidka
        Label offerLabel = new Label();
        offerLabel.setText("Nabídka:");
        TextField offerField = new TextField();


        //Tlacitko - submit
        Button submitButton = new Button();
        submitButton.setText("Potvrdit");
        submitButton.setOnAction(event -> {
            Application app = new Application(editStage);
        });

        //Tlacitko - cancel
        Button cancelButton = new Button();
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
        gridPane.add(descLabel,0,4);
        gridPane.add(descField,1,4);
        gridPane.add(offerLabel,0,5);
        gridPane.add(offerField,1,5);
        gridPane.add(boxButtons,1,6);

        //Window - setup
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane, 450, 300);

        editStage = new Stage();
        editStage.setTitle("Aplikace káva - Přihlášení");
        editStage.setScene(scene);
        editStage.show();
    }
}
