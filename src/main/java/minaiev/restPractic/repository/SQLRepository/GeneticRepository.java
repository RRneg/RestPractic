package minaiev.restPractic.repository.SQLRepository;

import java.util.List;

public interface GeneticRepository<T, ID> {

    T getById(ID id);

    T update(T t);

    void deleteById(ID id);

    List<T> getAll();

    T save(T t);

}
