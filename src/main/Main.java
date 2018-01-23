package main;

import GUI.Login;
import GUI.Registration;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.*;


import java.awt.event.MouseEvent;

public class Main extends Application {

    public void start(Stage primaryStage){
        BorderPane borderPane = new BorderPane();

        //Titulek
        Text title = new Text();
        title.setText("Vítejte v Informačním systému\npro kávové závisláky");
        title.getStyleClass().add("title");

        //Tlacitko - prihlaseni
        Button login = new Button();
        login.getStyleClass().add("submitButton");

        login.setText("Přihlášení");
        login.setOnAction(event -> {
            Login log = new Login(primaryStage);
        });

        //Tlacitko - registrace
        Button registration = new Button();
        registration.getStyleClass().add("blueButton");
        registration.setText("Registrace");
        registration.setOnAction(event -> {
            Registration reg = new Registration(primaryStage);
        });

        //VBox
        VBox vBox = new VBox();
        vBox.getChildren().addAll(title, login, registration);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        //Window - setup
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
