package GUI;

import interfaces.Observer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Cafe;
import logic.Database;

public class SearchPanel extends VBox implements Observer {

    private Database database;
    private Stage lastStage;
    private Label nameLabel;
    private Label nameDataLabel;
    private Label shortDescriptionLabel;
    private Label shortDescriptionDataLabel;
    private Label ratingLabel;
    private Label ratingDataLabel;
    private Button detailButton;


    public SearchPanel(Stage lastStage, Database database){
        this.lastStage = lastStage;
        this.database = database;
        database.registerObserver(this);
        update();
    }

    @Override
    public void update(){
        this.getChildren().clear();
        for(Cafe cafe: database.getCafe()){

            //LABEL - nazev kavarny
            nameLabel = new Label();
            nameLabel.setText("Název:");
            nameDataLabel = new Label();
            nameDataLabel.setText(cafe.getName());

            //LABEL - kratky popis
            shortDescriptionLabel = new Label();
            shortDescriptionLabel.setText("Popis:");
            shortDescriptionDataLabel = new Label();
            shortDescriptionDataLabel.setText(cafe.getShortDescription());

            //LABEL - hodnoceni
            ratingLabel = new Label();
            ratingLabel.setText("Hodnocení: ");
            ratingDataLabel = new Label();
            ratingDataLabel.setText("" + cafe.getRating());

            //BUTTON - detail kavarny
            detailButton = new Button();
            detailButton.setText("Detail");
            detailButton.setOnAction(event -> {
                int id = cafe.getId();
                DetailCafe detailCafe = new DetailCafe(lastStage,database,id);
            });

            //BUTTON Container
            BorderPane buttonPane = new BorderPane();
            buttonPane.setCenter(detailButton);

            //CAFE Container
            GridPane cafePane = new GridPane();
            cafePane.setAlignment(Pos.CENTER);
            cafePane.setHgap(5);
            cafePane.setVgap(0);
            cafePane.add(nameLabel,0,0);
            cafePane.add(nameDataLabel,1,0);
            cafePane.add(shortDescriptionLabel,0,1);
            cafePane.add(shortDescriptionDataLabel,1,1);
            cafePane.add(ratingLabel,0,2);
            cafePane.add(ratingDataLabel,1,2);

            //PANEL - CAFE + BUTTON
            HBox hBox = new HBox();
            hBox.setSpacing(25);
            hBox.getChildren().addAll(cafePane, buttonPane);

            this.getChildren().add(hBox);
        }

    }


}
