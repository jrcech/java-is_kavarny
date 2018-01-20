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

    /**
     *
     * @param lastStage Stage minuleho okna
     */
    public Registration(Stage lastStage){

        lastStage.hide();

        //Titulek - Osobní udaje
        Text titlePersonal = new Text();
        titlePersonal.setText("Osobní údaje:");

        //Titulek - Přihlašovací údaje
        Text titleRegistration = new Text();
        titleRegistration.setText("Přihlašovací Údaje:");

        //Form - Jmeno
        Label jmenoLabel = new Label();
        jmenoLabel.setText("Jméno:");
        TextField jmenoField = new TextField();
        jmenoField.setPromptText("Jan");

        //Form - Přijmení
        Label prijmeniLabel = new Label();
        prijmeniLabel.setText("Přijmení:");
        TextField prijmeniField = new TextField();
        prijmeniField.setPromptText("Novák");

        //Form - username
        Label userLabel = new Label();
        userLabel.setText("Nickname:");
        TextField userField = new TextField();
        userField.setPromptText("jizzy85");

        //Form - email
        Label emailLabel = new Label();
        emailLabel.setText("Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("jizzy@email.com");

        //Form - password
        Label passLabel = new Label();
        passLabel.setText("Heslo:");
        TextField passField = new TextField();
        passField.setPromptText("password");

        //Form - password znovu
        Label passConfirmLabel = new Label();
        passConfirmLabel.setText("Potvrď heslo:");
        TextField passConfirmField = new TextField();
        passConfirmField.setPromptText("password");

        //Tlacitko - submit
        Button submitButton = new Button();
        submitButton.setText("Registrovat");

        //Tlacitko - cancel
        Button cancelButton = new Button();
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
