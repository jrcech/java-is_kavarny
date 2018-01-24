package GUI;

import interfaces.Observer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Database;
import logic.Person;
import logic.Rating;

public class RatingPanel extends VBox implements Observer  {

    private Database database;
    private Stage lastStage;
    private int id;
    private Button deleteButton;
    private int idRating;

    public RatingPanel(Database database, int id){
        this.database = database;
        this.id = id;
        database.registerObserver(this);
        update();
    }

    @Override
    public void update(){
        this.getChildren().clear();
        for(Rating rating : database.getRating()){
            if(rating.getIdCafe() == id){

                //LABEL - Uzivatel
                Label userLabel = new Label();
                userLabel.setText("Uživatel:");
                Label userDataLabel = new Label();

                String sql = "SELECT * FROM sql11216990.person";
                database.operate("LOGIN", sql);

                for (Person person : database.getPerson()) {
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
                Label commentTextArea = new Label();
                commentTextArea.getStyleClass().add("label-small");
                commentTextArea.setText(rating.getComment());
                commentTextArea.setWrapText(true);

                //BUTTON - delete
                final int idRating = rating.getId();
                deleteButton = new Button();
                deleteButton.getStyleClass().add("deleteButton");
                deleteButton.setText("Smazat");
                deleteButton.setOnAction(event -> new DeleteRating(lastStage, database, idRating));

                //CAFE Container
                GridPane ratingPane = new GridPane();
                ratingPane.getStyleClass().add("ratingPane");
                ratingPane.setAlignment(Pos.TOP_LEFT);
                ratingPane.setHgap(10);
                ratingPane.setVgap(5);
                ratingPane.getColumnConstraints().add(new ColumnConstraints(85));
                ratingPane.getColumnConstraints().add(new ColumnConstraints(200));
                if(rating.getComment().length() > 0){
                    ratingPane.add(userLabel,0,0);
                    ratingPane.add(userDataLabel,1,0);
                    ratingPane.add(ratingLabel,0,1);
                    ratingPane.add(ratingDataLabel,1,1);
                    ratingPane.add(commentLabel,0,2);
                    ratingPane.add(commentTextArea,1,2);
                    if (database.getLoggedPerson().isAdmin() || database.getLoggedPerson().getId() == rating.getIdPerson()) {
                        ratingPane.add(deleteButton,1,3);
                    }
                }
                else{
                    ratingPane.add(userLabel,0,0);
                    ratingPane.add(userDataLabel,1,0);
                    ratingPane.add(ratingLabel,0,1);
                    ratingPane.add(ratingDataLabel,1,1);
                    if (database.getLoggedPerson().isAdmin() || database.getLoggedPerson().getId() == rating.getIdPerson()) {
                        ratingPane.add(deleteButton,1,3);
                    }
                }
                this.getChildren().addAll(ratingPane);


            }
        }
    }
}
