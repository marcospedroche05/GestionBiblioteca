package Modelo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class DAOGen <T, ID>{
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    private Class<T> clase;
    private ID id;

    public DAOGen(Class<T> clase, ID id) {
        this.clase = clase;
        this.id = id;
    }

    public void insert(T t) {
        tx.begin();
        em.persist(t);
        tx.commit();
    }

    public T getById(ID id) {
        return em.find(clase, id);
    }

    public List<T> getAll() {
        return em.createQuery("SELECT e FROM " + clase.getSimpleName() + " e ", clase).getResultList();
    }

    public void delete(T t) {
        tx.begin();
        em.remove(t);
        tx.commit();
    }

    public void update(T t) {
        tx.begin();
        em.merge(t);
        tx.commit();
    }
}
