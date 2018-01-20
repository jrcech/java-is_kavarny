package GUI;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Menu;

public class Application {

    private Stage appStage;
    private MenuPanel menuPanel;

    public Application(Stage lastStage){

        lastStage.hide();

        //Window - setup
        BorderPane borderPane = new BorderPane();
        appStage = new Stage();
        menuPanel = new MenuPanel(this, appStage, borderPane);
        appStage.setTitle("Aplikace káva - Hlavní obrazovka");
        Scene scene = new Scene(borderPane, 450, 300);
        appStage.setScene(scene);
        borderPane.setTop(menuPanel);

        appStage.show();

    }

}
