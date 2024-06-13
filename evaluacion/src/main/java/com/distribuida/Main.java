package com.distribuida;

import com.distribuida.db.Book;
import com.distribuida.servicios.IServicioBook;
import com.google.gson.Gson;
import io.helidon.http.Status;
import io.helidon.webserver.WebServer;
import jakarta.enterprise.inject.spi.CDI;
import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.spi.ContainerLifecycle;
import org.eclipse.persistence.oxm.MediaType;

import java.util.List;

public class Main {

    private static ContainerLifecycle lifecycle = null;

    public static void main(String[] args) {
        lifecycle = WebBeansContext.currentInstance().getService(ContainerLifecycle.class);
        lifecycle.startApplication(null);

        Gson gson = new Gson();
        IServicioBook servicio = CDI.current().select(IServicioBook.class).get();

        Book b = new Book();
        b.setIsbn("B001");
        b.setAuthor("Rowling");
        b.setTitle("Harry Potter");
        b.setPrice(19.2f);

        servicio.insertar(b);

        Book b1 = new Book();
        b1.setIsbn("B001");
        b1.setAuthor("Rowling");
        b1.setTitle("Harry Potter");
        b1.setPrice(19.2f);

        servicio.insertar(b1);

        Book b2 = new Book();
        b2.setIsbn("B001");
        b2.setAuthor("Rowling");
        b2.setTitle("Harry Potter");
        b2.setPrice(19.2f);

        servicio.insertar(b2);

        Book b3 = new Book();
        b3.setIsbn("B001");
        b3.setAuthor("Rowling");
        b3.setTitle("Harry Potter");
        b3.setPrice(19.2f);

        servicio.insertar(b3);

        WebServer server = WebServer.builder()
                .port(8080)
                .routing(builder -> builder
                        .get("/books", (req, res) -> {
                            try {
                                List<Book> libros = servicio.buscarLibros();
                                String response = gson.toJson(libros);
                                res.send(response);
                            } catch (Exception e) {
                                res.status(500).send("Error al obtener los libros");
                            }
                        })
                        .get("/books/{id}", (req, res) -> {
                            String iS = req.path().pathParameters().get("id");
                            Integer id = Integer.valueOf(iS);
                            Book l = servicio.buscarLibroPorId(id);
                            String response = gson.toJson(l);
                            res.send(response);
                        })
                        .put("/books/{id}", (req, res) -> {
                            String iS = req.path().pathParameters().get("id");
                            Integer id = Integer.valueOf(iS);
                            Book e = servicio.buscarLibroPorId(id);
                            if (e == null) {
                                res.send("No existe el libro");
                                return;
                            }
                            String body = req.content().as(String.class);
                            Book u = gson.fromJson(body, Book.class);
                            u.setId(id);
                            servicio.actualizar(u);
                            res.send("Actualizado");
                        })
                        .post("/books", (req, res) -> {
                            String bd = req.content().as(String.class);
                            Book lib = gson.fromJson(bd, Book.class);
                            servicio.insertar(lib);
                            res.send("Insertado");
                        })
                        .delete("/books/{id}", (req, res) -> {
                            String iS = req.path().pathParameters().get("id");
                            Integer id = Integer.valueOf(iS);
                            servicio.eliminar(id);
                            res.send("Eliminado");
                        })
                )
                .build();

        server.start();
    }
}
