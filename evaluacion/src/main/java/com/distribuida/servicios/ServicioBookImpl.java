package com.distribuida.servicios;

import com.distribuida.config.Jpaconfig;
import com.distribuida.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

@ApplicationScoped
public class ServicioBookImpl implements IServicioBook {

    @Inject
    EntityManager em;


    @Override
    public Book buscarLibroPorId(Integer id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> buscarLibros() {
        TypedQuery<Book> a=em.createQuery("SELECT b FROM Book b", Book.class);
        var m=a.getResultList();
        return m;
    }

    @Override
    public Book insertar(Book b) {
        em.persist(b);
        return b;
    }

    @Override
    public Book actualizar(Book b) {
        em.merge(b);
        return b;
    }

    @Override
    public void eliminar(Integer id) {
        em.remove(em.find(Book.class,id));
    }
}
