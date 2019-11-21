package genreic;

import java.util.Map;

public interface Validator<T> {

    boolean hasErrors();
    Map<String, String> getErrors();
    void validate(T t);

}
