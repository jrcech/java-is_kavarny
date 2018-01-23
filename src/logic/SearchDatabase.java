package logic;

import interfaces.Observer;
import interfaces.Subject;
import java.sql.*;
import java.util.*;

/** Class SearchDatabase
 *
 * @author cecj02
 * @version ZS 2017
 *
 */
public class SearchDatabase implements Subject {

    private List<Observer> listObserveru = new ArrayList<>();
    private Set<Cafe> dataCafe = new HashSet<>();
    private Set<Rating> dataRating = new HashSet<>();
    private Set<Person> dataPerson = new HashSet<>();
    private Person person;
    private final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private final String DB_CONNECTION = "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11216990?useUnicode=true&characterEncoding=UTF-8";
    private final String DB_USER = "sql11216990";
    private final String DB_PASSWORD = "76HIY8tGu2";
    private boolean first;

    /** Constructor
     *
     */
    public SearchDatabase() {
        first = true;
    }

    /** Operace s databazi
     *
     * @param option Typ operace
     * @param sql SQL dotaz
     * @return true – uspesna operace s db
     */
    public boolean databaseOperation(String option, String sql) {
        boolean value = false;
        Connection connection;
        PreparedStatement statement = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            ResultSet resultSet;
            if (option.equals("SEARCH")) {
                dataCafe.clear();
                dataRating.clear();
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
                    sql = "SELECT * FROM sql11216990.rating WHERE idCafe='" + id + "'";
                    statement = connection.prepareStatement(sql);
                    ResultSet rsRating = statement.executeQuery();
                    double rating = 0;
                    double count = 0;
                    while (rsRating.next()) {
                        int idRating = rsRating.getInt("id");
                        int idCafe = rsRating.getInt("idCafe");
                        int idPerson = rsRating.getInt("idPerson");
                        double commentRating = rsRating.getDouble("rating");
                        String comment = rsRating.getString("comment");
                        rating += commentRating;
                        count++;
                        dataRating.add(new Rating(idRating, idCafe, idPerson, commentRating, comment));
                    }
                    rating = rating / count;
                    rating = (int) Math.round(rating * 100) / (double) 100;
                    dataCafe.add(new Cafe(id, name, shortDescription, description, rating, address, region, coffeeBrand, event, specialOffer));
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
            if (option.equals("INSERT")) {
                statement = connection.prepareStatement(sql);
                statement.executeUpdate();
                value = true;
            }
            if (option.equals("DELETE")) {
                statement = connection.prepareStatement(sql);
                statement.executeUpdate();
                value = true;
            }
            statement.close();
            connection.close();
        } catch (SQLException | NullPointerException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        return value;
    }

    /** Nastaveni tridy Person na zaklade vysledku sql dotazu
     * 
     * @param resultSet Vysledek sql dotazu
     */
    private void personSetData(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                Boolean isAdmin = resultSet.getBoolean("isAdmin");

                //Kontrola prihlaseneho uzivatele
                if (first) {
                    person = new Person(id, username, firstName, lastName, email, isAdmin);
                    first = false;
                } else {
                    dataPerson.add(new Person(id, username, lastName, firstName, email, isAdmin));
                }
            }
        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }

    /** Ziskani prihlasene osoby
     * 
     * @return person Osoba
     */
    public Person getLoggedPerson() {
        return person;
    }

    /** Nastaveni noveho hodnoceni
     *
     * @param idRating ID hodnoceni
     * @param idCafe ID kavarny
     * @param idPerson ID osoby
     * @param commentRating Hodnota hodnoceni u kumentare
     * @param comment Komentar
     */
    public void setNewRating(int idRating, int idCafe, int idPerson, double commentRating, String comment) {
        dataRating.add(new Rating(idRating, idCafe, idPerson, commentRating, comment));
    }

    /** Ziskani dat kavaren
     *
     * @return dataCafe Kolekce dat kavaren
     */
    public Collection<Cafe> getCafe() {
        return Collections.unmodifiableCollection(dataCafe);
    }

    /** Ziskani dat hodnoceni
     *
     * @return dataRating Kolekce dat hodnoceni
     */
    public Collection<Rating> getRating() {
        return Collections.unmodifiableCollection(dataRating);
    }

    /** Ziskani seznamu osob
     *
     * @return dataPerson Kolekce osob
     */
    public Collection<Person> getPerson() {
        return Collections.unmodifiableCollection(dataPerson);
    }

    /** Pridani observeru
     *
     * @param observer Observer
     */
    @Override
    public void registerObserver(Observer observer) {
        listObserveru.add(observer);
    }

    /** Odebrani observeru
     *
     * @param observer Observer
     */
    @Override
    public void cancelObserver(Observer observer) {
        listObserveru.remove(observer);
    }

    /** Aktualizace observeru
     *
     */
    @Override
    public void notifyAllObservers() {
        listObserveru.stream().forEach((observer) -> {
            observer.update();
        });
    }
}
