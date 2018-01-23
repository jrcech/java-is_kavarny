package logic;


import interfaces.Observer;
import interfaces.Subject;

import java.sql.*;
import java.util.*;

/**
 *
 *
 * @author
 * @version
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

    /**
     *
     *
     * @param
     * @param
     * @return
     */
    public boolean databaseOperation(String option, String sql) {
        boolean value = false;
        Connection connection;
        PreparedStatement statement = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            ResultSet rs;
            if (option.equals("SEARCH")) {
                dataCafe.clear();
                dataRating.clear();
                statement = connection.prepareStatement(sql);
                rs = statement.executeQuery();
                if (rs.isBeforeFirst()) {
                    value = true;
                }
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String shortDescription = rs.getString("shortDescription");
                    String address = rs.getString("address");
                    String region = rs.getString("region");
                    String coffeeBrand = rs.getString("coffeeBrand");
                    String event = rs.getString("event");
                    String specialOffer = rs.getString("specialOffer");
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
                rs = statement.executeQuery();
                if (rs.isBeforeFirst()) {
                    personSetData(rs);
                    value = true;
                }
            }
            if (option.equals("SELECT")) {
                statement = connection.prepareStatement(sql);
                rs = statement.executeQuery();
                if (rs.isBeforeFirst()) {
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

    /**
     *
     *
     * @param
     */
    private void personSetData(ResultSet rs) {
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                Boolean isAdmin = rs.getBoolean("isAdmin");
                person = new Person(id, username, firstName, lastName, email, isAdmin);
                dataPerson.add(new Person(id, username, lastName, firstName, email, isAdmin));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    /**
     *
     * @return
     */
    public Person getLoggedPerson() {
        return person;
    }

    /**
     *
     * @param
     * @param
     * @param
     * @param
     */
    public void setNewRating(int idRating, int idCafe, int idPerson, double commentRating, String comment) {
        dataRating.add(new Rating(idRating, idCafe, idPerson, commentRating, comment));
    }

    /**
     *
     * @return
     */
    public Collection<Cafe> getCafe() {
        return Collections.unmodifiableCollection(dataCafe);
    }

    /**
     *
     * @return
     */
    public Collection<Rating> getRating() {
        return Collections.unmodifiableCollection(dataRating);
    }

    /**
     *
     * @return
     */
    public Collection<Person> getPerson() {
        return Collections.unmodifiableCollection(dataPerson);
    }

    /**
     *
     * @param
     */
    @Override
    public void registerObserver(Observer observer) {
        listObserveru.add(observer);
    }

    /**
     *
     * @param
     */
    @Override
    public void cancelObserver(Observer observer) {
        listObserveru.remove(observer);
    }

    /**
     */
    @Override
    public void notifyAllObservers() {
        listObserveru.stream().forEach((observer) -> {
            observer.update();
        });
    }
}
