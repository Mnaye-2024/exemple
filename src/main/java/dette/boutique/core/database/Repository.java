package dette.boutique.core.database;

import java.util.List;

public interface Repository<T> {
    void insert(T element);

    List<T> selectAll();
}