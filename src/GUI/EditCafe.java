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
import logic.Cafe;
import logic.Database;

public class EditCafe {

    private Database database;
    private Stage editStage;
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

    public EditCafe(Stage lastStage, Database database, int idCafe){
        this.database = database;
        lastStage.hide();

        //Titulek
        Text title = new Text();
        title.setText("Vyplňte údaje kavárny");
        title.getStyleClass().add("title");

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
        regionBox.getSelectionModel().selectFirst();

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
            for (Cafe cafe : database.getCafe()) {
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
        submitButton.getStyleClass().add("submitButton");
        submitButton.setText("Potvrdit");
        submitButton.setOnAction(event -> {
            //Data na string do promenych
            String nameString = nameField.getText();
            String addressString = addressField.getText();
            String regionString = (String) regionBox.getSelectionModel().getSelectedItem();
            String shortDescriptionString = shortDescField.getText();
            String descriptionString = descField.getText();
            String coffeeBrandString = coffeeBrandField.getText();
            String eventString = eventField.getText();
            String offerString = offerField.getText();
            if (regionString == null) {
                regionString = "";
            }
            //Validace dat
            boolean nameValid = database.validData("cafe", nameString);
            boolean addressValid = database.validData("cafe", addressString);
            boolean shortDescriptionValid = database.validData("cafe", shortDescriptionString);
            boolean descriptionValid = database.validData("desc", descriptionString);
            boolean coffeeBrandValid = database.validData("cafe", coffeeBrandString);
            boolean eventValid = database.validData("length150", eventString);
            boolean offerValid = database.validData("length150", offerString);
            //Validace v poradku ->
            if(nameValid && addressValid && shortDescriptionValid && descriptionValid && coffeeBrandValid && eventValid && offerValid){
                String sql;
                if (idCafe == 99999999) {
                    sql = "INSERT INTO sql11216990.cafe " + "(name, shortDescription, description, address, region, coffeeBrand, event, specialOffer) VALUES ("
                            + "'" + nameString + "','" + shortDescriptionString + "','" + descriptionString + "','" + addressString + "','" + regionString + "','" + coffeeBrandString + "','" + eventString + "','" + offerString + "')";
                } else {
                    sql = "UPDATE sql11216990.cafe SET name='" + nameString + "', shortDescription='" + shortDescriptionString + "', description='" + descriptionString + "', address='" + addressString + "', region='" + regionString + "', coffeeBrand='" + coffeeBrandString + "', event='" + eventString + "', specialOffer='" + offerString + "' WHERE cafe.id=" + idCafe ;

                }
                boolean databaseOperation = database.operate("UPDATE", sql);
                if (databaseOperation) {
                    String titleAlert = "Změna byla uložena";
                    String textAlert = "Nová data byla zapsána do databáze.";
                    database.alert(titleAlert, textAlert);
                    editStage.hide();
                    if (idCafe == 99999999) {
                        lastStage.show();
                    }
                } else {
                    String titleAlert = "Změna neproběhla.";
                    String textAlert = "Něco je špatně, zkuste to prosím později.";
                    database.alert(titleAlert, textAlert);
                }
            } else {
                //Vypise kde pridani kavarny selhalo
                validationError(nameValid, addressValid, shortDescriptionValid, descriptionValid, coffeeBrandValid, eventValid, offerValid);
            }

        });

        //Tlacitko - cancel
        cancelButton = new Button();
        cancelButton.setText("Zpět");
        cancelButton.setOnAction(event -> {
            editStage.hide();
            lastStage.show();
        });


        // TilePane - spojeni tlacitek
        HBox boxButtons = new HBox(15);
        boxButtons.getStyleClass().add("registrationBoxButtons");
        boxButtons.getChildren().addAll(submitButton, cancelButton);
        boxButtons.setAlignment(Pos.BASELINE_RIGHT);

        //GridPane - rozlozeni formulare
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(15);
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
        Scene scene = new Scene(borderPane, 350, 450);
        scene.getStylesheets().add("styles/styles.css");

        editStage = new Stage();
        editStage.setTitle("Aplikace káva - Edit kavárny");
        editStage.setScene(scene);
        editStage.show();
    }

    /** Vypsani chyb validace kavaren
     * @param nameValid Je nazev validni
     * @param addressValid Je adresa validni
     * @param shortDescriptionValid Je kratky popis validni
     * @param descriptionValid Je popis validni
     * @param coffeeBrandValid Je znacka validni
     * @param eventValid Je udalost validni
     * @param offerValid Je specialni nabidka validni
     */
    private void validationError(boolean nameValid, boolean addressValid, boolean shortDescriptionValid, boolean descriptionValid, boolean coffeeBrandValid, boolean eventValid, boolean offerValid) {
        String error = "Chyba";
        if (!nameValid) {
            nameLabel.setStyle("-fx-text-fill: red");
            error += "\nNázev musí obsahovat 3 až 50 znaků";
        }
        if (!addressValid) {
            addressLabel.setStyle("-fx-text-fill: red");
            error += "\nAdresa musí obsahovat 3 až 50 znaků";
        }
        if (!shortDescriptionValid) {
            shortDescLabel.setStyle("-fx-text-fill: red");
            error += "\nKrátký popis musí obsahovat 3 až 50 znaků";
        }
        if (!descriptionValid) {
            descLabel.setStyle("-fx-text-fill: red");
            error += "\nPopis musí obsahovat 3 až 350 znaků";
        }
        if (!coffeeBrandValid) {
            coffeeBrandLabel.setStyle("-fx-text-fill: red");
            error += "\nZnačka musí obsahovat 3 až 50 znaků";
        }
        if (!eventValid) {
            eventLabel.setStyle("-fx-text-fill: red");
            error += "\nUdálost musí obsahovat maximálně 150 znaků";
        }
        if (!offerValid) {
            offerLabel.setStyle("-fx-text-fill: red");
            error += "\nSpeciální nabídka musí obsahovat maximálně 150 znaků";
        }
        String title = "Chyba: ";
        database.alert(title, error);
    }
}
