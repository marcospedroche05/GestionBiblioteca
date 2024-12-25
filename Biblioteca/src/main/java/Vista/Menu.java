package Vista;

import Controlador.ControlEjemplares;
import Controlador.ControlLibros;
import Controlador.ControlPrestamos;
import Controlador.ControlUsuarios;
import Modelo.*;

import java.util.Scanner;

public class Menu {
    public static Scanner sc = new Scanner(System.in);

    public static void muestraMenuInicial(){
        System.out.println("Bienvenido al sistema de la biblioteca, elige una opción: ");
        System.out.println("1. Registar usuario");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Mostrar libros");
        System.out.println("4. Salir");
        elijeOpcionInicial();
    }

    public static void elijeOpcionInicial(){
        switch (sc.nextInt()){
            case 1: ControlUsuarios.registroUser(); break;
            case 2: ControlUsuarios.iniciaSesion(); break;
            case 3: ControlLibros.mostrarLibros(); break;
            case 4: salir(); break;
            default: System.out.println("Opcion incorrecta"); muestraMenuInicial(); break;
        }
    }

    public static void muestraMenuUNormal(Usuario usuario){
        System.out.println("Bienvenido " + usuario.getNombre() + ", elija una opción: ");
        System.out.println("--------------------------");
        System.out.println("1. Ver mis prestamos");
        System.out.println("2. Volver");
        elijeOpcionNormal(usuario);
    }

    public static void elijeOpcionNormal(Usuario usuario){
        switch (sc.nextInt()){
            case 1: ControlPrestamos.verPrestamos(usuario); break;
            case 2: muestraMenuInicial(); break;
            default: System.out.println("Opcion incorrecta"); muestraMenuUNormal(usuario); break;
        }
    }

    public static void muestraMenuUAdmin(Usuario usuario){
        System.out.println("Bienvenido administrador " + usuario.getNombre() + ", elija una opción: ");
        System.out.println("---------------------------------");
        System.out.println("1. Añadir un libro");
        System.out.println("2. Añadir un ejemplar");
        System.out.println("3. Stock disponible de un libro");
        System.out.println("4. Realizar un prestamo");
        System.out.println("5. Devolver un libro");
        System.out.println("6. Volver");
        elijeOpcionAdmin(usuario);
    }

    public static void elijeOpcionAdmin(Usuario usuario){
        switch (sc.nextInt()){
            case 1: ControlLibros.agregarLibro(); break;
            case 2: ControlEjemplares.registraEjemplar(); break;
            case 3: ControlEjemplares.getStock(); break;
            case 4: ControlPrestamos.realizaPrestamo(); break;
            case 5: ControlPrestamos.devolverPrestamo(); break;
            case 6: muestraMenuInicial(); break;
            default:System.out.println("Opcion incorrecta"); muestraMenuUAdmin(usuario); break;
        }
    }

    public static void salir(){
        System.out.println("SALIENDO DEL PROGRAMA...");
    }
}
