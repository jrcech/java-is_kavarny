package GUI;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import main.Main;

public class MenuPanel extends MenuBar {
    private Application application;
    private Stage appStage;
    private BorderPane borderPane;
    private Login login;

    public MenuPanel(Application application, Stage appStage, BorderPane borderPane){
        this.application = application;
        this.appStage = appStage;
        this.borderPane = borderPane;
        init();
    }
    public void init(){
        // 100 % width
        this.prefWidthProperty().bind(appStage.widthProperty());

        // MENU - Operace v Aplikaci
        Menu appMenu = new Menu("Program");
        MenuItem logOut = new MenuItem("Odhlásit se");
        MenuItem end = new MenuItem("Ukončit program");
        appMenu.getItems().addAll(logOut, end);
        logOut.setOnAction(event -> {
            appStage.hide();
            Login log = new Login(appStage);
        });
        end.setOnAction(actionEvent -> Platform.exit());

        // MENU - Napoveda
        Menu helpMenu = new Menu("Nápověda");
        MenuItem about = new MenuItem("O aplikaci");
        helpMenu.getItems().addAll(about);
        about.setOnAction(event -> {
            Alert oProgramu = new Alert(Alert.AlertType.INFORMATION);
            oProgramu.setTitle("Informace - O Programu");
            oProgramu.setHeaderText("Informační systém - Kavárny");
            oProgramu.setContentText("Aplikace je vytvořena do předmětu 4IT115\n"+
                    "Tým Kávoholiků:\n"+
                    "Vítězslav Slavík\n" +
                    "Jiří Čech\n" +
                    "Petra Puškárová" +
                    "Katrin Medovarská" +
                    "Nikola Trníková");
            oProgramu.initOwner(appStage);
            oProgramu.showAndWait();
        });
        // Spojeni do MenuBar
        this.getMenus().addAll(appMenu, helpMenu);
    }
}
