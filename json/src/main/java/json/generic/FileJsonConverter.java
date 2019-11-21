package json.generic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.MyException;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public abstract class FileJsonConverter<T> implements GenericConverter<T> {

    private final Type typeGeneric = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * @return optional of some class object
     */
    @Override
    public Optional<T> fromJson(String jsonFilename, Type type) {
        try (FileReader fileReader = new FileReader(jsonFilename)) {
            return Optional.of(gson.fromJson(fileReader, type));
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
    }

    public Optional<T> fromJson(String jsonFilename) {
        try (FileReader fileReader = new FileReader(jsonFilename)) {
            return Optional.of(gson.fromJson(fileReader, typeGeneric));
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public void toJson(final T element, final String jsonFilename) {
        try (FileWriter fileWriter = new FileWriter(jsonFilename)) {
            if (element == null) {
                throw new MyException("ELEMENT IS NULL EXCEPTION - FILE JSON CONVERTER");
            }
            gson.toJson(element, fileWriter);
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
    }

}


