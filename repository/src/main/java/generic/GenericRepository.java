package generic;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T> {
     Optional<T> addOrUpdate(T t);

     void delete(Long id);

     Optional<T> findById(Long id);

     List<T> findAll();

}
