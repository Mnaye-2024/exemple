package dette.boutique.core.repository.impl;

import java.util.List;

import dette.boutique.core.repository.Repository;
import jakarta.persistence.EntityManager;

public class RepositoryJpaImpl<T> implements Repository<T> {
    protected EntityManager em;
    protected Class<T> type;

    public RepositoryJpaImpl(Class<T> type, EntityManager em) {
        this.type = type;
        this.em = em;
    }

    @Override
    public void insert(T entity) {
        try {
            em.getTransaction().begin();
            if (em.contains(entity)) {
                em.merge(entity);
            } else {
                em.persist(entity);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<T> selectAll() {
        List<T> resultList = null;
        try {
            resultList = em.createQuery("SELECT e FROM " + type.getSimpleName() + " e", type).getResultList();
            // Pas de commit ou rollback ici car ce n'est pas une transaction
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public T selectById(int id) {
        try {
            return em.find(type, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void remove(T element) {
        try {
            em.getTransaction().begin();

            T managedElement = em.contains(element) ? element : em.merge(element);
            
            em.remove(managedElement);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

}