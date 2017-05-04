package luna.framework.syntax.data;

/**
 * Created by macbookair on 27/04/17.
 */

public interface LunaHashMapAdapter {

    void create(Object adaptee);
    int size();
    boolean containsKey(String key);
    Object get(String key);
}
