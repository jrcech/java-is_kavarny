package interfaces;

/**
 *
 *
 * @author
 * @version
 * 
 */
public interface Subject {
    
    /**
     *
     * @param
     */
    public void registerObserver(Observer observer);

    /**
     *
     * @param
     */
    public void cancelObserver(Observer observer);

    /**
     * 
     *
     */
    public void notifyAllObservers();
}
