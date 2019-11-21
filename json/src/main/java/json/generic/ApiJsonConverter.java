package json.generic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.MyException;

import java.io.FileWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public abstract class ApiJsonConverter<T> implements GenericConverter<T> {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void toJson(final T element, final String filename) {
        try (FileWriter fw = new FileWriter(filename)) {
            if (element == null) {
                throw new MyException("ELEMENT IS NULL EXCEPTION - API JSON CONVERTER");
            }
            gson.toJson(element, fw);
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
    }

    /**
     * @return optional of some class object
     */
    @Override
    public Optional<T> fromJson(String jsonText, Type type) {
        try {
            return Optional.of(gson.fromJson(jsonText, type));
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
    }
}
