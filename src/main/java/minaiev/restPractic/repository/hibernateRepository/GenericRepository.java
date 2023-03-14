package minaiev.restPractic.repository.hibernateRepository;

import java.util.List;

public interface GenericRepository<T, ID> {

    T getById(ID id);

    T update(T t);

    void deleteById(ID id);

    List<T> getAll();

    T save(T t);

}
