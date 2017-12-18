package logic;

/** Class Person
 * @author slav02
 * @version ZS 2017
 */

public class Cafe {

    private int id;
    private String name;
    private String description;
    private double rating;
    private String region;
    private String address;
    private String coffeeBrand;
    private String event;
    private String specialOffer;

    /** Constructor
     * @param id            id
     * @param name          nazev
     * @param description   popis
     * @param rating        celkove hodnoceni
     * @param region        kraj
     * @param address       adresa
     * @param coffeeBrand   znacka kavy
     * @param event         udalost
     * @param specialOffer  specialni nabidky
     */
    public Cafe (int id, String name, String description, double rating, String address, String region, String coffeeBrand, String event, String specialOffer) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.region = region;
        this.address = address;
        this.coffeeBrand = coffeeBrand;
        this.event = event;
        this.specialOffer = specialOffer;
    }
}
