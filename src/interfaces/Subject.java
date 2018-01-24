package interfaces;

/** Interface Subject s observery
 *
 * @author cecj02
 * @version ZS 2018
 * 
 */
public interface Subject {

    /** Pridani observeru
     * @param observer Observer
     */
    public void registerObserver(Observer observer);

    /** Odebrani observeru
     * @param observer Observer
     */
    public void cancelObserver(Observer observer);

    /** Aktualizace observeru
     */
    public void notifyAllObservers();
}
