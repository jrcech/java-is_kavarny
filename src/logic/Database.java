package logic;

import interfaces.Idatabase;
import javafx.scene.control.Alert;

/**
 *
 *
 * @author
 * @version
 *
 */
public class Database implements Idatabase {

    private SearchDatabase searchDatabase = new SearchDatabase();

    /**
     *
     *
     * @param
     * @param
     * @return
     */
    @Override
    public boolean validData(String option, String name) {
        switch (option) {
            case "name":
                if (name.length() >= 3 && name.length() <= 30) {
                    return true;
                }
                break;
            case "email":
                String valEm = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\"
                        + ".[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
                java.util.regex.Pattern pEm = java.util.regex.Pattern.compile(valEm);
                java.util.regex.Matcher mEm = pEm.matcher(name);
                if (mEm.matches()) {
                    return true;
                }
                break;
            case "cafe":
                if (name.length() >= 3 && name.length() <= 50) {
                    return true;
                }
                break;
            case "desc":
                if (name.length() >= 3 && name.length() <= 350) {
                    return true;
                }
                break;
            case "special":
                if (name.length() >= 0 && name.length() <= 150) {
                    return true;
                }
                break;
            case "choice":
                if (name.length() >= 1 && name.length() <= 50) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean validPasswd(String passwd1, String passwd2) {
        if (passwd1.equals(passwd2)) {
            String valPa = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
            java.util.regex.Pattern pPa = java.util.regex.Pattern.compile(valPa);
            java.util.regex.Matcher mPa = pPa.matcher(passwd2);
            if (mPa.matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     *
     * @param
     * @param
     */
    @Override
    public void alert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     *
     * @return vrací třídu SearchDatabase
     */
    @Override
    public SearchDatabase getSearchDatabase() {
        return searchDatabase;
    }

}
