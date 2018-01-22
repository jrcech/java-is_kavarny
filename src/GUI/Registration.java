package GUI;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Registration {

    private Stage registrationStage;
    private Text titlePersonal;
    private Text titleRegistration;
    private Label jmenoLabel;
    private Label prijmeniLabel;
    private Label userLabel;
    private Label emailLabel;
    private Label passLabel;
    private Label passConfirmLabel;
    private TextField jmenoField;
    private TextField prijmeniField;
    private TextField userField;
    private TextField emailField;
    private TextField passField;
    private TextField passConfirmField;
    private Button submitButton;
    private Button cancelButton;

    /**
     *
     * @param lastStage Stage minuleho okna
     */
    public Registration(Stage lastStage){

        lastStage.hide();

        //Titulek - Osobní udaje
        titlePersonal = new Text();
        titlePersonal.setText("Osobní údaje:");

        //Titulek - Přihlašovací údaje
        titleRegistration = new Text();
        titleRegistration.setText("Přihlašovací Údaje:");

        //Form - Jmeno
        jmenoLabel = new Label();
        jmenoLabel.setText("Jméno:");
        jmenoField = new TextField();
        jmenoField.setPromptText("Jan");

        //Form - Přijmení
        prijmeniLabel = new Label();
        prijmeniLabel.setText("Přijmení:");
        prijmeniField = new TextField();
        prijmeniField.setPromptText("Novák");

        //Form - username
        userLabel = new Label();
        userLabel.setText("Nickname:");
        userField = new TextField();
        userField.setPromptText("jizzy85");

        //Form - email
        emailLabel = new Label();
        emailLabel.setText("Email:");
        emailField = new TextField();
        emailField.setPromptText("jizzy@email.com");

        //Form - password
        passLabel = new Label();
        passLabel.setText("Heslo:");
        passField = new TextField();
        passField.setPromptText("password");

        //Form - password znovu
        passConfirmLabel = new Label();
        passConfirmLabel.setText("Potvrď heslo:");
        passConfirmField = new TextField();
        passConfirmField.setPromptText("password");

        //Tlacitko - submit
        submitButton = new Button();
        submitButton.setText("Registrovat");

        //Tlacitko - cancel
        cancelButton = new Button();
        cancelButton.setText("Zpět");
        cancelButton.setOnAction(event -> {
            registrationStage.hide();
            lastStage.show();
        });

        // TilePane - spojeni tlacitek

        HBox boxButtons = new HBox(5);
        boxButtons.getChildren().addAll(submitButton, cancelButton);
        boxButtons.setAlignment(Pos.BASELINE_RIGHT);

        //GridPane - rozlozeni formulare
        GridPane firstGridPane = new GridPane();
        firstGridPane.setAlignment(Pos.CENTER);
        firstGridPane.setHgap(10);
        firstGridPane.setVgap(10);
        firstGridPane.add(titlePersonal,0,0, 2,1);
        firstGridPane.add(jmenoLabel,0,1);
        firstGridPane.add(jmenoField,1,1);
        firstGridPane.add(prijmeniLabel,0,2);
        firstGridPane.add(prijmeniField,1,2);

        //GridPane - rozlozeni formulare
        GridPane secondGridPane = new GridPane();
        secondGridPane.setAlignment(Pos.CENTER);
        secondGridPane.setHgap(10);
        secondGridPane.setVgap(10);
        secondGridPane.add(titleRegistration,0,0, 2,1);
        secondGridPane.add(userLabel,0,1);
        secondGridPane.add(userField,1,1);
        secondGridPane.add(emailLabel,0,2);
        secondGridPane.add(emailField,1,2);
        secondGridPane.add(passLabel,0,3);
        secondGridPane.add(passField,1,3);
        secondGridPane.add(passConfirmLabel,0,4);
        secondGridPane.add(passConfirmField,1,4);
        secondGridPane.add(boxButtons,1,5);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(firstGridPane,secondGridPane);
        vBox.setSpacing(15);
        vBox.setAlignment(Pos.CENTER);






























        


        //Window - setup
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vBox);
        Scene scene = new Scene(borderPane, 450, 300);

        registrationStage = new Stage();
        registrationStage.setTitle("Aplikace káva - Registrace");
        registrationStage.setScene(scene);
        registrationStage.show();

    }
}
