package logic;

/** Class Person
 * @author slav02
 * @version ZS 2017
 */
public class Rating {

    private int id;
    private int idCafe;
    private int idPerson;
    private double rating;
    private String comment;

    /** Constructor
     * @param id        id
     * @param idCafe    id kavarny
     * @param idPerson  id uzivatele
     * @param rating    hodnoceni
     * @param comment   komentar
     */
    public Rating (int id, int idCafe, int idPerson, double rating, String comment) {
        this.id = id;
        this.idCafe = idCafe;
        this.idPerson = idPerson;
        this.rating = rating;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public int getIdCafe() {
        return idCafe;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public double getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
