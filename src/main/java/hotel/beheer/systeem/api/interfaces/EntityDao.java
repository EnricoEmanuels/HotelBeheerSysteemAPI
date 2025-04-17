package hotel.beheer.systeem.api.interfaces;

import java.util.List;

public interface EntityDao<T> {
    void save(T entity);
    List<T> findAll();
    void deleteById(Integer id);
    T findById(Integer id);
    void update(T entity);
}
