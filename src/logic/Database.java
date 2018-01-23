package logic;

import interfaces.Observer;
import interfaces.Subject;
import javafx.scene.control.Alert;
import java.sql.*;
import java.util.*;

/** Napojeni a operace s databazi
 * @author cecj02
 * @version ZS 2017
 */
public class Database implements Subject {

    private List<Observer> listObserveru = new ArrayList<>();
    private Set<Cafe> hashCafe = new HashSet<>();
    private Set<Rating> hashRating = new HashSet<>();
    private Set<Person> hashPerson = new HashSet<>();
    private Person person;
    private boolean isLoggedIn;
    private final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private final String DB_CONNECTION = "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11216990?useUnicode=true&characterEncoding=UTF-8";
    private final String DB_USER = "sql11216990";
    private final String DB_PASSWORD = "76HIY8tGu2";

    /** Constructor
     * Nastavi isLoggedIn pro prihlaseneho uzivatele
     */
    public Database() {
        isLoggedIn = true;
    }

    /** Operace s databazi
     * @param option Typ operace
     * @param sql SQL dotaz
     * @return true – uspesna operace s db
     */
    public boolean operate(String option, String sql) {
        boolean value = false;
        Connection connection;
        PreparedStatement statement = null;

        try {
            //Pripojeni k databazi
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            ResultSet resultSet;
            if (option.equals("SEARCH")) {
                hashCafe.clear();
                hashRating.clear();
                statement = connection.prepareStatement(sql);
                resultSet = statement.executeQuery();
                if (resultSet.isBeforeFirst()) {
                    value = true;
                }
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    String shortDescription = resultSet.getString("shortDescription");
                    String address = resultSet.getString("address");
                    String region = resultSet.getString("region");
                    String coffeeBrand = resultSet.getString("coffeeBrand");
                    String event = resultSet.getString("event");
                    String specialOffer = resultSet.getString("specialOffer");

                    //Hodnoceni pro dano kavarnu
                    sql = "SELECT * FROM sql11216990.rating WHERE idCafe='" + id + "'";
                    statement = connection.prepareStatement(sql);
                    ResultSet resultSetRating = statement.executeQuery();
                    double rating = 0;
                    double count = 0;
                    while (resultSetRating.next()) {
                        int idRating = resultSetRating.getInt("id");
                        int idCafe = resultSetRating.getInt("idCafe");
                        int idPerson = resultSetRating.getInt("idPerson");
                        double ratingValue = resultSetRating.getDouble("rating");
                        String comment = resultSetRating.getString("comment");

                        //Pricteni hodnoceni a inkrementace poctu
                        rating += ratingValue;
                        count++;

                        //Pridani hodnoceni do hashe
                        hashRating.add(new Rating(idRating, idCafe, idPerson, ratingValue, comment));
                    }

                    //Vypocet prumerneho hodnoceni kavarny
                    rating = rating / count;
                    rating = (int) Math.round(rating * 100) / (double) 100;

                    //Pridani kavarny do hashe
                    hashCafe.add(new Cafe(id, name, shortDescription, description, rating, address, region, coffeeBrand, event, specialOffer));
                }
                notifyAllObservers();
            }
            if (option.equals("LOGIN")) {
                statement = connection.prepareStatement(sql);
                resultSet = statement.executeQuery();
                if (resultSet.isBeforeFirst()) {
                    personSetData(resultSet);
                    value = true;
                }
            }
            if (option.equals("SELECT")) {
                statement = connection.prepareStatement(sql);
                resultSet = statement.executeQuery();
                if (resultSet.isBeforeFirst()) {
                    value = true;
                }
            }
            if (option.equals("UPDATE") || option.equals("DELETE")) {
                statement = connection.prepareStatement(sql);
                statement.executeUpdate();
                value = true;
            }
            statement.close();
            connection.close();
        } catch (SQLException | NullPointerException | ClassNotFoundException exception) {
            System.out.println(exception);
        }
        return value;
    }

    /** Nastaveni tridy Person na zaklade vysledku sql dotazu
     * @param resultSet Vysledek sql dotazu
     */
    private void personSetData(ResultSet resultSet) {
        try {
            //Proiterovani vysledkem SQL dotazu
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                Boolean isAdmin = resultSet.getBoolean("isAdmin");

                //Kontrola prihlaseneho uzivatele
                if (isLoggedIn) {
                    //Vytvoreni instance Person pro prihlaseneho uzivatele
                    person = new Person(id, username, firstName, lastName, email, isAdmin);
                    isLoggedIn = false;
                } else {
                    //Pridani osoby do hashe
                    hashPerson.add(new Person(id, username, lastName, firstName, email, isAdmin));
                }
            }
        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }

    /** Validace dat
     * @param option Typ validace
     * @param data String urceny k validaci
     * @return true – Kontrolovana data jsou validni
     */
    public boolean validData(String option, String data) {
        switch (option) {
            case "name":
                if (data.length() >= 3 && data.length() <= 30) {
                    return true;
                }
                break;
            case "email":
                String valEm = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\"
                        + ".[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
                java.util.regex.Pattern pEm = java.util.regex.Pattern.compile(valEm);
                java.util.regex.Matcher mEm = pEm.matcher(data);
                if (mEm.matches()) {
                    return true;
                }
                break;
            case "cafe":
                if (data.length() >= 3 && data.length() <= 50) {
                    return true;
                }
                break;
            case "desc":
                if (data.length() >= 3 && data.length() <= 350) {
                    return true;
                }
                break;
            case "length150":
                if (data.length() >= 0 && data.length() <= 150) {
                    return true;
                }
                break;
            case "choice":
                if (data.length() >= 1 && data.length() <= 50) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    /** Validace hesla
     * @param password Heslo
     * @param passwordConfirm Kontrola hesla
     * @return true – heslo je validní
     */
    public boolean validPasswd(String password, String passwordConfirm) {
        //Kontrola shody hesla
        if (password.equals(passwordConfirm)) {
            //Regex kontolujici podminky hesla
            String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

            //Kompilace regex vzoru
            java.util.regex.Pattern compile = java.util.regex.Pattern.compile(pattern);

            //Uziti regexu na heslo
            java.util.regex.Matcher result = compile.matcher(passwordConfirm);

            //Kontrola validace
            if (result.matches()) {
                return true;
            }
        }
        return false;
    }

    /** Upozorneni
     * @param title Titulek upozorneni
     * @param text Text upozorneni
     */
    public void alert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    /** Ziskani prihlasene osoby
     * @return person Osoba
     */
    public Person getLoggedPerson() {
        return person;
    }

    /** Nastaveni noveho hodnoceni
     * @param idRating ID hodnoceni
     * @param idCafe ID kavarny
     * @param idPerson ID osoby
     * @param commentRating Hodnota hodnoceni u kumentare
     * @param comment Komentar
     */
    public void setNewRating(int idRating, int idCafe, int idPerson, double commentRating, String comment) {
        hashRating.add(new Rating(idRating, idCafe, idPerson, commentRating, comment));
    }

    /** Ziskani dat kavaren
     * @return hashCafe Kolekce dat kavaren
     */
    public Collection<Cafe> getCafe() {
        return Collections.unmodifiableCollection(hashCafe);
    }

    /** Ziskani dat hodnoceni
     * @return hashRating Kolekce dat hodnoceni
     */
    public Collection<Rating> getRating() {
        return Collections.unmodifiableCollection(hashRating);
    }

    /** Ziskani seznamu osob
     * @return hashPerson Kolekce osob
     */
    public Collection<Person> getPerson() {
        return Collections.unmodifiableCollection(hashPerson);
    }

    /** Pridani observeru
     * @param observer Observer
     */
    @Override
    public void registerObserver(Observer observer) {
        listObserveru.add(observer);
    }

    /** Odebrani observeru
     * @param observer Observer
     */
    @Override
    public void cancelObserver(Observer observer) {
        listObserveru.remove(observer);
    }

    /** Aktualizace observeru
     */
    @Override
    public void notifyAllObservers() {
        listObserveru.stream().forEach((observer) -> {
            observer.update();
        });
    }
}
