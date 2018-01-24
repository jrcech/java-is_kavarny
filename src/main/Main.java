package main;

import GUI.Login;
import GUI.Registration;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.*;

/** Uvodni obrazovka systemu
 * @author slav02
 * @version ZS 2018
 */
public class Main extends Application {

    public void start(Stage primaryStage){
        BorderPane borderPane = new BorderPane();

        //TITLE
        Text title = new Text();
        title.setText("Vítejte v Informačním systému\npro kávové závisláky");
        title.getStyleClass().add("title");

        //BUTTON - prihlaseni -> login obrazovka
        Button login = new Button();
        login.getStyleClass().add("submitButton");
        login.setText("Přihlášení");
        login.setOnAction(event -> new Login(primaryStage));

        //BUTTON - registrace -> obrazovka s registraci
        Button registration = new Button();
        registration.getStyleClass().add("blueButton");
        registration.setText("Registrace");
        registration.setOnAction(event -> new Registration(primaryStage));

        //VBOX - Titulek + Prihlaseni + Registrace
        VBox vBox = new VBox();
        vBox.getChildren().addAll(title, login, registration);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        //WINDOW - setup
        borderPane.setCenter(vBox);
        Scene scene = new Scene(borderPane, 450, 300);
        scene.getStylesheets().add("styles/styles.css");
        primaryStage.setTitle("Aplikace káva");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
