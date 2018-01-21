package interfaces;

import logic.SearchDatabase;

/**
 *
 * @author
 * @version
 * 
 */
public interface Idatabase {

    /**
     *
     * @param
     * @param
     * @return
     */
    public boolean validData(String option, String name);

    /**
     *
     * @param
     * @param
     * @return
     */
    public boolean validPasswd(String passwd1, String passwd2);
    /**
     *
     * @param
     * @param
     */
    public void alert(String title, String text);

    /**
     *
     * @return
     */
    public SearchDatabase getSearchDatabase();
}
