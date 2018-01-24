package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Cafe;
import logic.Database;
import logic.Rating;

public class DetailCafe {

    private Stage detailStage;
    private Database database;
    private RatingPanel ratingPanel;
    private Label nameLabel;
    private Label addressLabel;
    private Label regionLabel;
    private Label shortDescLabel;
    private Label descLabel;
    private Label offerLabel;
    private Label coffeeBrandLabel;
    private Label eventLabel;
    private Label addRatingLabel;
    private Label addCommentLabel;
    private Label name;
    private Label address;
    private Label region;
    private Label shortDesc;
    private Label desc;
    private Label offer;
    private Label coffeeBrand;
    private Label event;
    private TextArea addCommentArea;

    private Button addRatingButton;
    private Button editButton;
    private Button deleteButton;
    private Button cancelButton;

    public DetailCafe(Stage lastStage, Database database, int idCafe){

        this.database = database;
        //ID prihlasene osoby
        int idPerson = database.getLoggedPerson().getId();
        ratingPanel = new RatingPanel(database, idCafe);

        //aktualizace dat
        String sql1 = "SELECT * FROM sql11216990.cafe";
        database.operate("SEARCH", sql1);
        //Titulek
        Text title = new Text();
        title.setText("Detailní údaje kavárny");
        title.getStyleClass().add("title");

        //Form - Nazev
        nameLabel = new Label();
        nameLabel.setText("Název:");
        name = new Label();

        //Form - Adresa
        addressLabel = new Label();
        addressLabel.setText("Adresa:");
        address = new Label();

        //Form - Kraje
        regionLabel = new Label();
        regionLabel.setText("Kraj:");
        region = new Label();

        //Form - Popis
        shortDescLabel = new Label();
        shortDescLabel.setText("Krátký popis:");
        shortDesc = new Label();

        //Form - Popis
        descLabel = new Label();
        descLabel.setText("Popis:");
        descLabel.setWrapText(true);
        desc = new Label();
        desc.setWrapText(true);

        //Form - Nabidka
        offerLabel = new Label();
        offerLabel.setText("Nabídka:");
        offer = new Label();

        //Form - Znacka kavy
        coffeeBrandLabel = new Label();
        coffeeBrandLabel.setText("Značka kávy: ");
        coffeeBrand = new Label();

        //Form - Udalost
        eventLabel = new Label();
        eventLabel.setText("Událost:");
        eventLabel.setWrapText(true);
        event = new Label();

        //Tlacitko - edit cafe
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

        //Pridat Rating - Titulek
        Text addRatingTitle = new Text();
        addRatingTitle.setText("Přidejte vlastní hodnocení");
        addRatingTitle.getStyleClass().add("title-small");

        //Rating Ostatnich - Titulek
        Text otherRatingTitle = new Text();
        otherRatingTitle.setText("Hodnocení ostatních");
        otherRatingTitle.getStyleClass().add("title-small");

        //Form - Rating
        addRatingLabel = new Label();
        addRatingLabel.setText("Hodnocení:");
        ComboBox addRatingValue = new ComboBox();
        addRatingValue.getItems().addAll(5.0, 4.5, 4.0, 3.5, 3.0, 2.5, 2.0, 1.5, 1.0);
        addRatingValue.getSelectionModel().selectFirst();

        //Form - Comment
        addCommentLabel = new Label();
        addCommentLabel.setText("Komentář:");
        addCommentArea = new TextArea();
        addCommentArea.setWrapText(true);
        addCommentArea.setPrefRowCount(4);

        //Tlacitko - submit rating
        addRatingButton = new Button();
        addRatingButton.getStyleClass().add("submitButton");
        addRatingButton.setText("Odeslat hodnocení");
        addRatingButton.setOnAction(event -> {
            Double rating = (Double) addRatingValue.getSelectionModel().getSelectedItem();
            String comment = addCommentArea.getText();
            String ratingVal = String.valueOf(rating);
            if (comment == null) {
                comment = "";
            }
            boolean ratingValid = database.validData("choice", ratingVal);
            boolean commentValid = database.validData("length150", comment);
            if(ratingValid && commentValid){
                String sql = "SELECT * FROM sql11216990.rating WHERE idCafe='" + idCafe + "' AND idPerson='" + idPerson + "'";
                boolean databaseOperation = database.operate("SELECT", sql);

                if(!databaseOperation){
                    sql = "INSERT INTO sql11216990.rating " + "(idCafe, idPerson, rating, comment) VALUES ('" + idCafe + "','" + idPerson + "','" + rating + "','" + comment + "')";
                }
                else {
                    sql = "UPDATE sql11216990.rating SET idCafe='" + idCafe + "', idPerson='" + idPerson + "', rating='" + rating + "', comment='" + comment + "' WHERE idCafe='" + idCafe + "' AND idPerson='" + idPerson + "'";
                }
                databaseOperation = database.operate("UPDATE", sql);
                if (databaseOperation) {
                    database.setNewRating(idPerson, idCafe, idPerson, rating, comment);
                    String titleAlert = "Vaše hodnocení bylo uloženo";
                    String textAlert = "Vaše hodnocení bylo uloženo do systému. Děkujeme";
                    database.alert(titleAlert, textAlert);

                    detailStage.hide();
                    new DetailCafe(lastStage, database, idCafe);
                }
                else {
                    String titleAlert = "Hodnocení se nepodařilo uložit";
                    String textAlert = "Omlouváme se, váše hodnocení se nepodařilo uložit.";
                    database.alert(titleAlert, textAlert);
                }
            } else {
                //Vypise kde pridani hodnoceni selhalo
                validationError(commentValid);
            }

        });

        //LOOP - Priradi hodnoty do formulare
        for (Cafe cafe : database.getCafe()) {
            if (idCafe == cafe.getId()) {
                name.setText(cafe.getName());
                address.setText(cafe.getAddress());
                region.setText(cafe.getRegion());
                shortDesc.setText(cafe.getShortDescription());
                desc.setText(cafe.getDescription());
                coffeeBrand.setText(cafe.getCoffeeBrand());
                event.setText(cafe.getEvent());
                offer.setText(cafe.getSpecialOffer());
                for (Rating rating : database.getRating()) {
                    if (idCafe == rating.getIdCafe() && idPerson == rating.getIdPerson()) {
                        addCommentArea.setText(rating.getComment());
                        addRatingValue.setValue(rating.getRating());
                    }
                }
            }
        }

        //Tlacitko - submit
        editButton = new Button();
        editButton.getStyleClass().add("blueButton");
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

        //Tlacitko - delete
        deleteButton = new Button();
        deleteButton.getStyleClass().add("deleteButton");
        deleteButton.setText("Smazat");
        deleteButton.setOnAction(event -> new DeleteCafe(detailStage, database, idCafe));

        // TilePane - spojeni tlacitek
        HBox boxButtons = new HBox(5);
        boxButtons.setAlignment(Pos.BASELINE_RIGHT);
        if (!database.getLoggedPerson().isAdmin()) {
            boxButtons.getChildren().addAll(cancelButton);
        } else {
            boxButtons.getChildren().addAll(editButton, cancelButton, deleteButton);
        }

        //GridPane - Pridani hodnoceni
        GridPane addRatingPane = new GridPane();
        addRatingPane.setAlignment(Pos.BASELINE_LEFT);
        addRatingPane.setHgap(10);
        addRatingPane.setVgap(10);
        addRatingPane.getColumnConstraints().add(new ColumnConstraints(100));
        addRatingPane.getColumnConstraints().add(new ColumnConstraints(200));
        addRatingPane.add(addRatingTitle,0,0, 2,1);
        addRatingPane.add(addRatingLabel,0,1);
        addRatingPane.add(addRatingValue,1,1);
        addRatingPane.add(addCommentLabel,0,2);
        addRatingPane.add(addCommentArea,1,2);
        addRatingPane.add(addRatingButton,1,3);

        //ScrollPane - dostupne hodnoceni uzivatelu
        ScrollPane ratingPane = new ScrollPane();
        //ratingPane.setStyle("-fx-background-color:transparent;");
        ratingPane.setContent(ratingPanel);

        VBox addRatingWrapper = new VBox();
        addRatingWrapper.getChildren().addAll(addRatingPane, otherRatingTitle, ratingPane);

        //GridPane - detailni informace o kavarne
        GridPane detailPane = new GridPane();
        detailPane.setAlignment(Pos.TOP_CENTER);
        detailPane.getColumnConstraints().add(new ColumnConstraints(100));
        detailPane.getColumnConstraints().add(new ColumnConstraints(300));
        detailPane.setHgap(10);
        detailPane.setVgap(15);
        detailPane.add(title,0,0, 2,1);
        detailPane.add(nameLabel,0,1);
        detailPane.add(name,1,1);
        detailPane.add(addressLabel,0,2);
        detailPane.add(address,1,2);
        detailPane.add(regionLabel,0,3);
        detailPane.add(region,1,3);
        detailPane.add(shortDescLabel,0,4);
        detailPane.add(shortDesc,1,4);
        detailPane.add(descLabel,0,5);
        detailPane.add(desc,1,5);
        detailPane.add(coffeeBrandLabel,0,6);
        detailPane.add(coffeeBrand,1,6);
        detailPane.add(eventLabel,0,7);
        detailPane.add(event,1,7);
        detailPane.add(offerLabel,0,8);
        detailPane.add(offer,1,8);
        detailPane.add(boxButtons,1,9);


        GridPane gridPane = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(55);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(45);
        gridPane.getColumnConstraints().addAll(col1,col2);
        detailPane.setAlignment(Pos.CENTER);
        gridPane.add(detailPane,0,0);
        gridPane.add(addRatingWrapper,1,0);


        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("panePadding");
        borderPane.setCenter(gridPane);


        detailStage = new Stage();
        Scene scene = new Scene(borderPane, 900, 600);
        scene.getStylesheets().add("styles/styles.css");
        detailStage.setTitle("Aplikace káva - Detail Kavárny");
        detailStage.setScene(scene);
        detailStage.show();
        detailStage.setResizable(false);
    }

    /** Vypsani chyb validace komentaru
     * @param commentValid Je komentar validni
     */
    private void validationError(boolean commentValid) {
        String error = "Chyba";
        if (!commentValid) {
            addCommentLabel.setStyle("-fx-text-fill: red");
            error += "\nKomentář musí mít maximálně 150 znaků";
        }

        String title = "Chyba: ";
        database.alert(title, error);
    }
}
