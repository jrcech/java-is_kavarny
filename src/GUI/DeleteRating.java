package GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.Database;

public class DeleteRating {
    private Stage deleteStage;

    public DeleteRating(Stage lastStage, Database database, int idRating) {

        //Tlacitko - confirm
        Button confirmButton = new Button();
        confirmButton.setText("Potvrdit smazání");
        confirmButton.setOnAction(event -> {

            //Smazani kavarny z databaze
            String sql = "DELETE FROM sql11216990.rating WHERE rating.id='" + idRating + "'";
            boolean databaseOperation = database.operate("DELETE", sql);

            //Vysledek pozadavku smazani
            if (databaseOperation) {
                String titleAlert = "Hodnocení bylo smazáno";
                String textAlert = "Úspěšně jste smazali hodnocení";
                database.alert(titleAlert, textAlert);
                deleteStage.hide();
            } else {
                String titleAlert = "Hodnocení nebylo smazáno";
                String textAlert = "Došlo k chybě";
                database.alert(titleAlert, textAlert);
            }
        });

        //Tlacitko - cancel
        Button cancelButton = new Button();
        cancelButton.setText("Zpět");
        cancelButton.setOnAction(event -> {
            deleteStage.hide();
            lastStage.show();
        });

        //GridPane - rozlozeni okna pro potvrzeni smazani
        GridPane gridPane = new GridPane();
        gridPane.add(confirmButton, 1, 1);
        gridPane.add(cancelButton, 2, 1);

        //Window - setup
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane, 450, 400);

        deleteStage = new Stage();
        deleteStage.setTitle("Aplikace káva - Smazání hodnocení");
        deleteStage.setScene(scene);
        deleteStage.show();
    }
}
