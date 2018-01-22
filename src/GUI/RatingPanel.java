package GUI;

import interfaces.Idatabase;
import interfaces.Observer;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
        
    }
}
