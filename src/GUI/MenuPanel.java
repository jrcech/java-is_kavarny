package GUI;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import main.Main;

/** Panel zobrazujici hodnoceni kavarny
 * @author slav02 / cejc02
 * @version ZS 2018
 */
public class MenuPanel extends MenuBar {
    private Application application;
    private Stage appStage;
    private BorderPane borderPane;

    public MenuPanel(Application application, Stage appStage, BorderPane borderPane){
        this.application = application;
        this.appStage = appStage;
        this.borderPane = borderPane;
        init();
    }

    /** Vytvari menu **/
    public void init(){
        // 100 % width
        this.prefWidthProperty().bind(appStage.widthProperty());

        //MENU - Operace v Aplikaci
        Menu appMenu = new Menu("Program");
        MenuItem logOut = new MenuItem("Odhlásit se");
        MenuItem end = new MenuItem("Ukončit program");
        appMenu.getItems().addAll(logOut, end);
        logOut.setOnAction(event -> {
            appStage.hide();
            Main main = new Main();
            main.start(appStage);
        });
        end.setOnAction(actionEvent -> Platform.exit());

        //MENU - Napoveda
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
                    "Petra Puškárová\n" +
                    "Katrin Medovarská\n" +
                    "Nikola Trníková");
            oProgramu.initOwner(appStage);
            oProgramu.showAndWait();
        });

        //Spojeni do MenuBar
        this.getMenus().addAll(appMenu, helpMenu);
    }
}
