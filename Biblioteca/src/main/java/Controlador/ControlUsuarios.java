package Controlador;

import Modelo.DAOUsuario;
import Modelo.Usuario;
import Vista.Menu;

public class ControlUsuarios {
    private static final DAOUsuario daoUsuario = new DAOUsuario(Usuario.class, Integer.class);


    public static void iniciaSesion(){
        System.out.println("INICIO DE SESION");
        System.out.println("-----------------------");
        System.out.println("Correo electrónico: ");
        String correo = Menu.sc.next();
        System.out.println("Contraseña: ");
        String password = Menu.sc.next();
        Usuario usuario = daoUsuario.getUsuarioByCorreo(correo);
        if(usuario.getPassword().equals(password)){
            if(usuario.getTipo().equals("normal"))
                Menu.muestraMenuUNormal(usuario);
            else Menu.muestraMenuUAdmin(usuario);
        }
        else System.out.println("Contraseña incorrecta.");
    }

    public static void registroUser(){
        System.out.println("REGISTRO DE USUARIO");
        System.out.println("-------------------------");
        System.out.println("Introduce el DNI: ");
        String dni = Menu.sc.next();
        System.out.println("\nIntroduce el nombre: ");
        String nombre = Menu.sc.next();
        System.out.println("\nIntroduce el correo electrónico: ");
        String correo = Menu.sc.next();
        System.out.println("\nIntroduce la contraseña: ");
        String password = Menu.sc.next();
        System.out.println("\n¿Es administrador o normal? (N/A)");
        String tipo = Menu.sc.next();

        Usuario nuevoUsuario = new Usuario(dni, nombre, correo, password, tipo.toUpperCase());
        if(daoUsuario.getUsuarioByCorreo(correo) != null){
            if(nuevoUsuario.getTipo().equals("N"))
                nuevoUsuario.setTipo("normal");
            else if(nuevoUsuario.getTipo().equals("A"))
                nuevoUsuario.setTipo("administrador");
            daoUsuario.insert(nuevoUsuario);
            System.out.println("Usuario registrado correctamente.");
        } else System.out.println("Ya existe un usuario con el mismo correo");
        Menu.muestraMenuInicial();
    }

    public static Usuario obtenerUsuarioId(Integer id){
        return (Usuario)daoUsuario.getById(id);
    }

    public static void actualizaUsuario(Usuario usuario){
        daoUsuario.update(usuario);
    }
}
