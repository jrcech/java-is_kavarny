package logic;

/** Class Person
 * @author slav02
 * @version ZS 2017
 **/

public class Person {

    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isAdmin;

    /** Constructor
    * @param id id
    * @param username   prezdivka
    * @param firstName  jmeno
    * @param lastName   prijmeni
    * @param email      email
    * @param isAdmin    je admin**/
    public Person(int id, String username, String firstName, String lastName, String email, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isAdmin = isAdmin;
    }

}
