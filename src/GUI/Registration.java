package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Database;

public class Registration {

    private Database database;
    private Stage registrationStage;
    private Text titlePersonal;
    private Text titleRegistration;
    private Label firstNameLabel;
    private Label lastNameLabel;
    private Label usernameLabel;
    private Label emailLabel;
    private Label passwordLabel;
    private Label passwordConfirmLabel;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField usernameField;
    private TextField emailField;
    private PasswordField passwordField;
    private PasswordField passwordConfirmField;
    private Button submitButton;
    private Button cancelButton;

    /**
     *
     * @param lastStage Stage minuleho okna
     */
    public Registration(Stage lastStage){
        database = new Database();
        lastStage.hide();

        //Titulek - Osobní udaje
        titlePersonal = new Text();
        titlePersonal.setText("Osobní údaje:");

        //Titulek - Přihlašovací údaje
        titleRegistration = new Text();
        titleRegistration.setText("Přihlašovací Údaje:");

        //Form - Jmeno
        firstNameLabel = new Label();
        firstNameLabel.setText("Jméno:");
        firstNameField = new TextField();
        firstNameField.setPromptText("Jan");

        //Form - Přijmení
        lastNameLabel = new Label();
        lastNameLabel.setText("Přijmení:");
        lastNameField = new TextField();
        lastNameField.setPromptText("Novák");

        //Form - username
        usernameLabel = new Label();
        usernameLabel.setText("Nickname:");
        usernameField = new TextField();
        usernameField.setPromptText("jizzy85");

        //Form - email
        emailLabel = new Label();
        emailLabel.setText("Email:");
        emailField = new TextField();
        emailField.setPromptText("jizzy@email.com");

        //Form - password
        passwordLabel = new Label();
        passwordLabel.setText("Heslo:");
        passwordField = new PasswordField();
        passwordField.setPromptText("password");

        //Form - password znovu
        passwordConfirmLabel = new Label();
        passwordConfirmLabel.setText("Potvrď heslo:");
        passwordConfirmField = new PasswordField();
        passwordConfirmField.setPromptText("password");

        //Tlacitko - submit
        submitButton = new Button();
        submitButton.setText("Registrovat");
        submitButton.setOnAction(event -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String passwordConfirm = passwordConfirmField.getText();

            boolean validUsername = database.validData("name", username);
            boolean validFirstName = database.validData("name", firstName);
            boolean validLastName = database.validData("name", lastName);
            boolean validEmail = database.validData("email", email);
            boolean validPassword = database.validPasswd(password, passwordConfirm);

            //Kontrola volneho username v db
            String sql = "SELECT * FROM sql11216990.person WHERE username='" + username + "'";
            boolean databaseOperation = database.getSearchDatabase().databaseOperation("SELECT", sql);

            if (validUsername && validFirstName && validLastName && validEmail && validPassword) {
                if (!databaseOperation) {
                    sql = "INSERT INTO sql11216990.person " + "(username, firstName, lastName, email, password, isAdmin) VALUES (" + "'" + username + "','" + firstName + "','" + lastName + "','" + email + "','" + password + "', 0" + ")";
                    database.getSearchDatabase().databaseOperation("INSERT", sql);
                    String title = "Registrace úspěšně dokončena";
                    String text = "Můžete se přihlásit";
                    database.alert(title, text);
                    registrationStage.hide();
                    lastStage.show();
                } else {
                    String title = "Registrace se nezdařila";
                    String text = "Tento nickname již existuje";
                    database.alert(title, text);
                    usernameLabel.setStyle("-fx-text-fill: red");
                }
            } else {
                //Vypise kde registrace selhala
                validationError(validUsername, validFirstName, validLastName, validEmail, validPassword);
            }
        });

        //Tlacitko - cancel
        cancelButton = new Button();
        cancelButton.setText("Zpět");
        cancelButton.setOnAction(event -> {
            registrationStage.hide();
            lastStage.show();
        });

        //TilePane - spojeni tlacitek
        HBox boxButtons = new HBox(5);
        boxButtons.getChildren().addAll(submitButton, cancelButton);
        boxButtons.setAlignment(Pos.BASELINE_RIGHT);

        //GridPane - rozlozeni formulare
        GridPane firstGridPane = new GridPane();
        firstGridPane.setAlignment(Pos.CENTER);
        firstGridPane.setHgap(10);
        firstGridPane.setVgap(10);
        firstGridPane.add(titlePersonal,0,0, 2,1);
        firstGridPane.add(firstNameLabel,0,1);
        firstGridPane.add(firstNameField,1,1);
        firstGridPane.add(lastNameLabel,0,2);
        firstGridPane.add(lastNameField,1,2);

        //GridPane - rozlozeni formulare
        GridPane secondGridPane = new GridPane();
        secondGridPane.setAlignment(Pos.CENTER);
        secondGridPane.setHgap(10);
        secondGridPane.setVgap(10);
        secondGridPane.add(titleRegistration,0,0, 2,1);
        secondGridPane.add(usernameLabel,0,1);
        secondGridPane.add(usernameField,1,1);
        secondGridPane.add(emailLabel,0,2);
        secondGridPane.add(emailField,1,2);
        secondGridPane.add(passwordLabel,0,3);
        secondGridPane.add(passwordField,1,3);
        secondGridPane.add(passwordConfirmLabel,0,4);
        secondGridPane.add(passwordConfirmField,1,4);
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

    //Vypis chyb registrace
    private void validationError(boolean validUsername,
                                 boolean validFirstName,
                                 boolean validLastName,
                                 boolean validEmail,
                                 boolean validPasswd) {
        String error = "Chyba: ";
        if (!validUsername) {
            usernameLabel.setStyle("-fx-text-fill: red");
            error += "\nNickname musí obsahovat 3 až 30 znaků";
        }
        if (!validFirstName) {
            firstNameLabel.setStyle("-fx-text-fill: red");
            error += "\nJméno musí obsahovat 3 až 30 znaků";
        }
        if (!validLastName) {
            lastNameLabel.setStyle("-fx-text-fill: red");
            error += "\nPříjmení musí obsahovat 3 až 30 znaků";
        }
        if (!validEmail) {
            emailLabel.setStyle("-fx-text-fill: red");
            error += "\nEmail musí být ve tvaru exaple@exaple.com";
        }
        if (!validPasswd) {
            passwordLabel.setStyle("-fx-text-fill: red");
            passwordConfirmLabel.setStyle("-fx-text-fill: red");
            error += "\nHesla se musí shodovat a musí obsahovat 8 až 30 znaků, " +
                    "jedno malé písmeno, jedno velké písmeno a jedno číslo";
        }
        String title = "Chyba: ";
        database.alert(title, error);
    }
}
