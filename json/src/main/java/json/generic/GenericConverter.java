package json.generic;

import java.lang.reflect.Type;
import java.util.Optional;

public interface GenericConverter<T> {
     void toJson(final T element, final String jsonFilename);
     Optional<T> fromJson(String jsonFilename, Type type);
}
