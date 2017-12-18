package main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage){
        BorderPane borderPane = new BorderPane();

        //Tlacitko - prihlaseni
        Button login = new Button();
        login.setText("Přihlášení");

        //Tlacitko - registrace
        Button registration = new Button();
        registration.setText("Registrace");

        ////Window - setup
        FlowPane center = new FlowPane();
        center.setAlignment(Pos.CENTER);
        center.getChildren().addAll(login, registration);

        borderPane.setCenter(center);
        Scene scene = new Scene(borderPane, 450, 300);
        primaryStage.setTitle("Aplikace káva");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
