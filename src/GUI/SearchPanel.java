package GUI;

import interfaces.Idatabase;
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

public class SearchPanel extends VBox implements Observer {

    private Idatabase database;
    private Stage lastStage;

    public SearchPanel(Stage lastStage,Idatabase database){
        this.lastStage = lastStage;
        this.database = database;
        database.getSearchDatabase().registerObserver(this);
        update();
    }

    @Override
    public void update(){
        this.getChildren().clear();
        for(Cafe cafe: database.getSearchDatabase().getCafe()){

            //LABEL - nazev kavarny
            Label nameLabel = new Label();
            nameLabel.setText("Název:");
            Label nameDataLabel = new Label();
            nameDataLabel.setText(cafe.getName());

            //LABEL - kratky popis
            Label shortDescriptionLabel = new Label();
            shortDescriptionLabel.setText("Popis:");
            Label shortDescriptionDataLabel = new Label();
            shortDescriptionDataLabel.setText(cafe.getShortDescription());

            //LABEL - hodnoceni
            Label ratingLabel = new Label();
            ratingLabel.setText("Hodnocení: ");
            Label ratingDataLabel = new Label();
            ratingDataLabel.setText("" + cafe.getRating());

            //BUTTON - detail kavarny
            Button detailButton = new Button();
            detailButton.setText("Detail");

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
