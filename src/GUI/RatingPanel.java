package GUI;

import interfaces.Idatabase;
import interfaces.Observer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Person;
import logic.Rating;

public class RatingPanel extends VBox implements Observer  {

    private Idatabase database;
    private Stage lastStage;
    private int id;

    public RatingPanel(Idatabase database, int id){
        this.database = database;
        this.id = id;
        database.getSearchDatabase().registerObserver(this);
        update();
    }

    @Override
    public void update(){
        this.getChildren().clear();
        for(Rating rating : database.getSearchDatabase().getRating()){
            if(rating.getIdCafe() == id){

                //LABEL - Uzivatel
                Label userLabel = new Label();
                userLabel.setText("Uživatel:");
                Label userDataLabel = new Label();
                for (Person person : database.getSearchDatabase().getPerson()) {
                    if (person.getId() == rating.getIdPerson()) {
                        userDataLabel.setText(person.getUsername());
                        break;
                    }
                }

                //LABEL - Rating
                Label ratingLabel = new Label();
                ratingLabel.setText("Hodnocení:");
                Label ratingDataLabel = new Label();
                ratingDataLabel.setText("" + rating.getRating());

                //LABEL + TEXTAREA - Comment
                Label commentLabel = new Label();
                commentLabel.setText("Komentář:");
                TextArea commentTextArea = new TextArea();
                commentTextArea.setText(rating.getComment());
                commentTextArea.setPrefRowCount(2);
                commentTextArea.setPrefColumnCount(10);
                commentTextArea.setWrapText(true);
                commentTextArea.setEditable(false);

                //CAFE Container
                GridPane ratingPane = new GridPane();
                ratingPane.setAlignment(Pos.TOP_LEFT);
                ratingPane.setHgap(5);
                ratingPane.setVgap(0);
                if(rating.getComment().length() > 0){
                    ratingPane.add(userLabel,0,0);
                    ratingPane.add(userDataLabel,1,0);
                    ratingPane.add(ratingLabel,0,1);
                    ratingPane.add(ratingDataLabel,1,1);
                    ratingPane.add(commentLabel,0,2);
                    ratingPane.add(commentTextArea,1,2);
                }
                else{
                    ratingPane.add(userLabel,0,0);
                    ratingPane.add(userDataLabel,1,0);
                    ratingPane.add(ratingLabel,0,1);
                    ratingPane.add(ratingDataLabel,1,1);
                }
                this.getChildren().addAll(ratingPane);


            }
        }
    }
}
