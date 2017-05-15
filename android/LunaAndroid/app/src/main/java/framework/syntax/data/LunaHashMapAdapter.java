package framework.syntax.data;

/**
 * Created by macbookair on 27/04/17.
 */

public interface LunaHashMapAdapter {


    Integer size();
    Boolean containsKey(String key);
    Object get(String key);
    String[] keys();
}
