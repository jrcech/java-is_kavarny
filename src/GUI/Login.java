package GUI;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login {

    private Stage loginStage;

    /**
     *
     * @param lastStage Stage minul0ho okna
     */
    public Login(Stage lastStage){

        lastStage.hide();

        BorderPane borderPane = new BorderPane();

        //Titulek
        Text title = new Text();
        title.setText("Zadejte přihlašovací údaje");

        Label passWord = new Label();
        passWord.setText("Heslo:");

        //Form - username
        Label userLabel = new Label();
        userLabel.setText("Účet:");
        TextField userField = new TextField();
        userField.setText("jizzy");

        //Form - password
        Label passLabel = new Label();
        passLabel.setText("Heslo:");
        TextField passField = new TextField();
        passField.setText("default");

        //Tlacitko - submit
        Button submitButton = new Button();
        submitButton.setText("Přihlásit");

        //Tlacitko - cancel
        Button cancelButton = new Button();
        cancelButton.setText("Zpět");
        cancelButton.setOnAction(event -> {
            loginStage.hide();
            lastStage.show();
        });

        // TilePane - spojeni tlacitek
        TilePane tileButtons = new TilePane(Orientation.HORIZONTAL);
        tileButtons.setVgap(5);
        tileButtons.getChildren().addAll(submitButton, cancelButton);
        tileButtons.setAlignment(Pos.TOP_RIGHT);

        //GridPane - rozlozeni formulare
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(title,0,0, 2,1);
        gridPane.add(userLabel,0,1);
        gridPane.add(userField,1,1);
        gridPane.add(passLabel,0,2);
        gridPane.add(passField,1,2);
        gridPane.add(tileButtons,1,3);

        //Window - setup
        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane, 450, 300);

        loginStage = new Stage();
        loginStage.setTitle("Aplikace káva - Přihlášení");
        loginStage.setScene(scene);
        loginStage.show();

    }
}
