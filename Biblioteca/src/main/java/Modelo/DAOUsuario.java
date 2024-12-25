package Modelo;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class DAOUsuario extends DAOGen{
    public DAOUsuario(Class clase, Object o) {
        super(clase, o);
    }
    public Usuario getUsuarioByCorreo(String correo) {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        Query consulta = em.createNativeQuery(sql, Usuario.class);
        consulta.setParameter(1, correo);
        try {
            return (Usuario) consulta.getSingleResult();
        }catch (NoResultException nre){
             return null;
        }
    }
}
