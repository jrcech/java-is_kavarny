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
    private TextField nameField;
    private TextField addressField;
    private TextField regionField;
    private TextField shortDescField;
    private TextArea descArea;
    private TextField offerField;
    private TextField coffeeBrandField;
    private TextField eventField;
    private TextArea addCommentArea;

    private Button addRatingButton;
    private Button editButton;
    private Button deleteButton;
    private Button cancelButton;

    public DetailCafe(Stage lastStage, Database database, int idCafe){

        this.database = database;
        //ID prihlasene osoby
        int idPerson = database.getOperateDatabase().getLoggedPerson().getId();
        ratingPanel = new RatingPanel(database, idCafe);

        //aktualizace dat
        String sql1 = "SELECT * FROM sql11216990.cafe WHERE id='" + idCafe + "'";
        database.getOperateDatabase().databaseOperation("SEARCH", sql1);
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
        //Rating Ostatnich - Titulek
        Text otherRatingTitle = new Text();
        otherRatingTitle.setText("Hodnocení ostatních");
        //Form - Rating
        addRatingLabel = new Label();
        addRatingLabel.setText("Hodnocení");
        ComboBox addRatingValue = new ComboBox();
        addRatingValue.getItems().addAll(5.0, 4.5, 4.0, 3.5, 3.0, 2.5, 2.0, 1.5, 1.0);

        //Form - Comment
        addCommentLabel = new Label();
        addCommentLabel.setText("Komentář");
        addCommentArea = new TextArea();
        addCommentArea.setWrapText(true);
        addCommentArea.setPrefRowCount(4);


        //Tlacitko - submit rating
        addRatingButton = new Button();
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
                boolean databaseOperation = database.getOperateDatabase().databaseOperation("SELECT", sql);

                if(!databaseOperation){
                    sql = "INSERT INTO sql11216990.rating " + "(idCafe, idPerson, rating, comment) VALUES ('" + idCafe + "','" + idPerson + "','" + rating + "','" + comment + "')";
                }
                else {
                    sql = "UPDATE sql11216990.rating SET idCafe='" + idCafe + "', idPerson='" + idPerson + "', rating='" + rating + "', comment='" + comment + "' WHERE idCafe='" + idCafe + "' AND idPerson='" + idPerson + "'";
                }
                databaseOperation = database.getOperateDatabase().databaseOperation("UPDATE", sql);
                if (databaseOperation) {
                    database.getOperateDatabase().setNewRating(idPerson, idCafe, idPerson, rating, comment);
                    String titleAlert = "Vaše hodnocení bylo uloženo";
                    String textAlert = "Vaše hodnocení bylo uloženo do systému. Děkujeme";
                    database.alert(titleAlert, textAlert);
                }
                else {
                    String titleAlert = "Hodnocení se nepodařilo uložit";
                    String textAlert = "Omlouváme se, váše hodnocení se nepodařilo uložit.";
                    database.alert(titleAlert, textAlert);
                }
            }

        });

        //LOOP - Priradi hodnoty do formulare
        for (Cafe cafe : database.getOperateDatabase().getCafe()) {
            if (idCafe == cafe.getId()) {
                nameField.setText(cafe.getName());
                addressField.setText(cafe.getAddress());
                regionField.setText(cafe.getRegion());
                shortDescField.setText(cafe.getShortDescription());
                descArea.setText(cafe.getDescription());
                coffeeBrandField.setText(cafe.getCoffeeBrand());
                eventField.setText(cafe.getEvent());
                offerField.setText(cafe.getSpecialOffer());
                for (Rating rating : database.getOperateDatabase().getRating()) {
                    if (idCafe == rating.getIdCafe() && idPerson == rating.getIdPerson()) {
                        addCommentArea.setText(rating.getComment());
                        addRatingValue.setValue(rating.getRating());
                    }
                }
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

        //Tlacitko - delete
        deleteButton = new Button();
        deleteButton.setText("Smazat");
        deleteButton.setOnAction(event -> new DeleteCafe(detailStage, database, idCafe));

        // TilePane - spojeni tlacitek
        HBox boxButtons = new HBox(5);
        boxButtons.setAlignment(Pos.BASELINE_RIGHT);
        if (!database.getOperateDatabase().getLoggedPerson().isAdmin()) {
            boxButtons.getChildren().addAll(cancelButton);
        } else {
            boxButtons.getChildren().addAll(editButton, cancelButton, deleteButton);
        }

        //GridPane - Pridani hodnoceni
        GridPane addRatingPane = new GridPane();
        addRatingPane.setAlignment(Pos.CENTER);
        addRatingPane.setHgap(5);
        addRatingPane.setVgap(5);
        addRatingPane.add(addRatingTitle,0,0, 2,1);
        addRatingPane.add(addRatingLabel,0,1);
        addRatingPane.add(addRatingValue,1,1);
        addRatingPane.add(addCommentLabel,0,2);
        addRatingPane.add(addCommentArea,1,2);
        addRatingPane.add(addRatingButton,1,3);

        //ScrollPane - dostupne hodnoceni uzivatelu
        ScrollPane ratingPane = new ScrollPane();
        ratingPane.setContent(ratingPanel);

        VBox addRatingWrapper = new VBox();
        addRatingWrapper.getChildren().addAll(addRatingPane, otherRatingTitle, ratingPane);

        //GridPane - detailni informace o kavarne
        GridPane detailPane = new GridPane();
        detailPane.setAlignment(Pos.CENTER);
        ColumnConstraints detailCol1 = new ColumnConstraints();
        detailCol1.setPercentWidth(20);
        ColumnConstraints detailCol2 = new ColumnConstraints();
        detailCol2.setPercentWidth(80);
        detailPane.getColumnConstraints().addAll(detailCol1,detailCol2);
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


        GridPane gridPane = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(60);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(40);
        gridPane.getColumnConstraints().addAll(col1,col2);
        detailPane.setAlignment(Pos.CENTER);
        gridPane.add(detailPane,0,0);
        gridPane.add(addRatingWrapper,1,0);


        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);


        detailStage = new Stage();
        Scene scene = new Scene(borderPane, 700, 500);
        detailStage.setTitle("Aplikace káva - Detail Kavárny");
        detailStage.setScene(scene);
        detailStage.show();
        detailStage.setResizable(false);
    }
}
