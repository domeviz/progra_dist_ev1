package com.distribuida.servicios;

import com.distribuida.db.Book;

import java.util.List;

public interface IServicioBook {
    Book buscarLibroPorId(Integer id);
    List<Book> buscarLibros();
    Book insertar(Book b);
    Book actualizar(Book b);
    void eliminar(Integer id);
}
