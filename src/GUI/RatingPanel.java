package GUI;

import interfaces.Idatabase;
import interfaces.Observer;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
                commentTextArea.setEditable(false);


            }
        };
    }
}
