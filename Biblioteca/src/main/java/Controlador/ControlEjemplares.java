package Controlador;

import Modelo.DAOGen;
import Modelo.Ejemplar;
import Modelo.Libro;
import Vista.Menu;

import java.util.ArrayList;
import java.util.List;

public class ControlEjemplares {
    private static final DAOGen daoEjemplares = new DAOGen<>(Ejemplar.class, Integer.class);

    public static void registraEjemplar(){
        System.out.println("Introduce el ISBN: ");
        String isbn = Menu.sc.next();
        Libro libro = ControlLibros.obtenerLibro(isbn);
        Ejemplar ejemplar = new Ejemplar(libro);
        daoEjemplares.insert(ejemplar);
    }

    public static void registraEjemplar(Ejemplar ejemplar){
        daoEjemplares.insert(ejemplar);
    }

    public static Ejemplar obtenerEjemplar(Integer id){
        return (Ejemplar) daoEjemplares.getById(id);
    }

    public static void getStock(){
        System.out.println("Introduce el ISBN: ");
        String isbn = Menu.sc.next();
        Integer stock = 0;
        ArrayList<Ejemplar> ejemplares = new ArrayList<>();
        for (Ejemplar ejemplar : (List<Ejemplar>) daoEjemplares.getAll()){
            if(ejemplar.getIsbn().getIsbn().equals(isbn)) ejemplares.add(ejemplar);
        }
        for (Ejemplar ejemplar : ejemplares){
            if(ejemplar.getEstado().equalsIgnoreCase("Disponible"))
                stock++;
        }
        System.out.println(stock);
    }

    public static void actualizaEjemplar(Ejemplar ejemplar){
        daoEjemplares.update(ejemplar);
    }
}
