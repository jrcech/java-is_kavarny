package GUI;

import interfaces.Idatabase;
import interfaces.Observer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
            nameLabel.setText("Název: " + cafe.getName());

            Label shortDescriptionLabel = new Label();
            shortDescriptionLabel.setText("Popis: " + cafe.getShortDescription());

            Label ratingLabel = new Label();
            ratingLabel.setText("Hodnocení: " + cafe.getRating());

            Button detailButton = new Button();
            detailButton.setText("Detail");

            VBox vBox = new VBox();
            vBox.getChildren().addAll(nameLabel,shortDescriptionLabel, ratingLabel);

            this.getChildren().add(vBox);
        }

    }


}
