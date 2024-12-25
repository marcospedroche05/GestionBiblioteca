package Modelo;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class DAOPrestamo extends DAOGen {

    public DAOPrestamo(Class clase, Object o) {
        super(clase, o);
    }

    public Prestamo getPrestamoByEjemplar(Ejemplar ejemplar){
        String sql = "SELECT * FROM prestamo WHERE ejemplar_id = ?";
        Query consulta = em.createNativeQuery(sql, Prestamo.class);
        consulta.setParameter(1, ejemplar.getId());
        try {
            return (Prestamo) consulta.getSingleResult();
        }catch (NoResultException nre){
            throw new RuntimeException("No existe un ejemplar con este ID");
        }
    }
}
