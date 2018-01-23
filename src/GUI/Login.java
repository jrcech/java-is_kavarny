package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Database;

public class Login {

    private Stage loginStage;
    private Database database;
    private Label userLabel;
    private TextField userField;
    private Label passLabel;
    private PasswordField passField;
    private Button submitButton;

    /**
     *
     * @param lastStage Stage minuleho okna
     */
    public Login(Stage lastStage){
        database = new Database();
        lastStage.hide();

        //Titulek
        Text title = new Text();
        title.setText("Zadejte přihlašovací údaje");

        //Form - username
        userLabel = new Label();
        userLabel.setText("Účet:");
        userField = new TextField();
        userField.setText("jizzy");

        //Form - password
        passLabel = new Label();
        passLabel.setText("Heslo:");
        passField = new PasswordField();
        passField.setText("12345Jizzy");

        //Button - submit
        submitButton = new Button();
        submitButton.setText("Přihlásit");
        submitButton.setOnAction(event -> {
            String name = userField.getText();
            String password = passField.getText();
            String sql = "SELECT * FROM sql11216990.person WHERE (username = '" + name + "' AND password = '" + password + "')";
            boolean databaseOperation = database.getSearchDatabase().databaseOperation("LOGIN", sql);
            if (databaseOperation) {
                loginStage.hide();
                Application app = new Application(loginStage, database);
            } else {
                String message = "Přihlášení se nezdařilo";
                String error = "Zadané údaje nejsou správné";
                database.alert(message, error);
            }
        });

        //Button - cancel
        Button cancelButton = new Button();
        cancelButton.setText("Zpět");
        cancelButton.setOnAction(event -> {
            loginStage.hide();
            lastStage.show();
        });

        // TilePane - spojeni tlacitek
        HBox boxButtons = new HBox(5);
        boxButtons.getChildren().addAll(submitButton, cancelButton);
        boxButtons.setAlignment(Pos.BASELINE_RIGHT);

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
        gridPane.add(boxButtons,1,3);

        //Window - setup
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane, 450, 300);

        loginStage = new Stage();
        loginStage.setTitle("Aplikace káva - Přihlášení");
        loginStage.setScene(scene);
        loginStage.show();

    }
    public Stage getPrimaryStage() {
        return loginStage;
    }
}
