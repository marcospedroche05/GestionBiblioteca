package Controlador;

import Modelo.*;
import Vista.Menu;

import java.time.LocalDate;
import java.util.List;


public class ControlPrestamos {
    private static final DAOPrestamo daoPrestamo = new DAOPrestamo(Prestamo.class, Integer.class);

    public static void realizaPrestamo() {
        System.out.println("Introduce el id del usuario que va a realizar el prestamo: ");
        Integer idUsuario = Menu.sc.nextInt();
        Usuario usuario = ControlUsuarios.obtenerUsuarioId(idUsuario);
        System.out.println("Introduce el id del ejemplar: ");
        Integer idEjemplar = Menu.sc.nextInt();
        //Si el usuario no tiene penalizaciones le permite realizar el prestamo
        if(usuario.getPenalizacionHasta() == null){
            //Si el usuario tiene menos de 3 prestamos puede realizar otro
            if(usuario.getPrestamos().size() < 3){
                //Si el ejemplar elegido está disponible realiza el prestamo
                if(ControlEjemplares.obtenerEjemplar(idEjemplar).getEstado().equals("Disponible")){
                    Ejemplar ejemplar = ControlEjemplares.obtenerEjemplar(idEjemplar);
                    //Cambio el estado a "Prestado" y actualizo la base de datos
                    ejemplar.setEstado("Prestado");
                    ControlEjemplares.actualizaEjemplar(ejemplar);
                    LocalDate fechaPrestamo = LocalDate.now();
                    LocalDate fechaDevolucion = fechaPrestamo.plusDays(15);
                    Prestamo prestamo = new Prestamo(usuario, ejemplar, fechaPrestamo, fechaDevolucion);
                    daoPrestamo.insert(prestamo);
                    System.out.println("Prestamo realizado, tiene hasta el " + fechaDevolucion + " para devolver el ejemplar.");
                } else System.out.println("El ejemplar seleccionado no está disponible");
            } else System.out.println("El usuario no puede realizar más de 3 prestamos al mismo tiempo");
        }else System.out.println("El usuario no puede realizar ningún prestamo debido a que tiene una penalización hasta el " + usuario.getPenalizacionHasta());
    }

    public static void devolverPrestamo() {
        System.out.println("Introduce el id del usuario que va a devolver un libro: ");
        Integer idUsuario = Menu.sc.nextInt();
        Usuario usuario = ControlUsuarios.obtenerUsuarioId(idUsuario);
        if(usuario.getPrestamos().isEmpty())
            System.out.println("Este usuario no tiene prestamos");
        else {
            System.out.println("¿Que ejemplar desea devolver?");
            System.out.println("--------------------------------");
            for (int i = 1; i <= usuario.getPrestamos().size(); i++) {
                Prestamo prestamo = usuario.getPrestamos().get(i-1);
                System.out.println("- " + prestamo.getEjemplar().getIsbn().getTitulo() + " ID: " + prestamo.getEjemplar().getId());
            }
            Ejemplar ejemplarElegido = ControlEjemplares.obtenerEjemplar(Menu.sc.nextInt());
            Prestamo prestamoElegido = daoPrestamo.getPrestamoByEjemplar(ejemplarElegido);
            //Si el usuario no tiene fecha de devolución establecida (caso practicamente imposible pero al ser un no nulo lo tomo en cuenta por si acaso)
            if(prestamoElegido.getFechaDevolucion() != null) {
                //Si el usuario devuelve un ejemplar mas tarde de la fecha de devolución entra en el bloque
                if (LocalDate.now().isAfter(prestamoElegido.getFechaDevolucion())) {
                    //Si el usuario no tiene penalización alguna aún, se le pone una de 15 días
                    if (usuario.getPenalizacionHasta() == null)
                        usuario.setPenalizacionHasta(LocalDate.now().plusDays(15));
                        //Si ya tiene penalizaciones anteriores, se le acumulan 15 días más
                    else
                        usuario.setPenalizacionHasta(usuario.getPenalizacionHasta().plusDays(15));
                    ControlUsuarios.actualizaUsuario(usuario);
                    System.out.println("Penalización establecida hasta " + usuario.getPenalizacionHasta());
                }
            }
            ejemplarElegido.setEstado("Disponible");
            ControlEjemplares.actualizaEjemplar(ejemplarElegido);
            actualizaPrestamo(prestamoElegido);
            eliminarPrestamo(prestamoElegido.getId());
            usuario.getPrestamos().remove(prestamoElegido);
            System.out.println("Ejemplar devuelto con éxito");
        }
    }

    public static void verPrestamos(Usuario usuario) {
        List<Prestamo> prestamos = usuario.getPrestamos();
        if(prestamos.isEmpty()) System.out.println("Este usuario no tiene prestamos");
        else {
            for (Prestamo prestamo : prestamos) {
                System.out.println(prestamo);
            }
        }
    }

    public static void actualizaPrestamo(Prestamo prestamo) {
        daoPrestamo.update(prestamo);
    }

    public static void eliminarPrestamo(Integer id) {
        daoPrestamo.delete(daoPrestamo.getById(id));
    }
}
