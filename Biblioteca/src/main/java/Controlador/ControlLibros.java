package Controlador;

import Modelo.DAOGen;
import Modelo.Ejemplar;
import Modelo.Libro;
import Vista.Menu;

import java.util.ArrayList;


public class ControlLibros {
    private static final DAOGen daoLibro = new DAOGen<>(Libro.class, String.class);

    public static void agregarLibro() {
        System.out.println("Introduce el ISBN del libro: ");
        String isbn = Menu.sc.next();
        System.out.println("Introduce el título del libro: ");
        String titulo = Menu.sc.next();
        System.out.println("Introduce el autor del libro: ");
        String autor = Menu.sc.next();
        Libro libro = new Libro(isbn, titulo, autor);
        daoLibro.insert(libro);
        ControlEjemplares.registraEjemplar(new Ejemplar(libro));
        System.out.println("Libro insertado correctamente");
    }

    public static Libro obtenerLibro(String isbn){
        return (Libro)daoLibro.getById(isbn);
    }

    public static void mostrarLibros(){
        ArrayList<Libro> librosBiblioteca = (ArrayList<Libro>) daoLibro.getAll();
        for(Libro libro : librosBiblioteca){
            System.out.println(libro);
        }
    }
}
