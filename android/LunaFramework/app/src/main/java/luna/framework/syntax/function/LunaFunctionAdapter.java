package luna.framework.syntax.function;

/**
 * Created by macbookair on 27/04/17.
 */

public interface LunaFunctionAdapter {

    void create(Object function);
    void execute();
    boolean isFunction();
}
