package com.issue_tracker.issue_tracker.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

@Repository
public class RequerimientoCodigoRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public Integer getCodigo(String tipo, int year) {

        // bloquear y obtener el contador actual
        String sql = "SELECT contador FROM requerimiento_codigos WHERE year = ? AND tipo = ? FOR UPDATE";
        List<Integer> resultados = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("contador"), year, tipo);

        if (resultados.isEmpty()) {

            // Si no existe un contador, insertar un nuevo registro
            String sqlInsert = "INSERT INTO requerimiento_codigos (year, tipo, contador) VALUES (?, ?, ?)";
            jdbcTemplate.update(sqlInsert, year, tipo, 1);

            return 1;
        } 

        // incrementar el contador
        String sqlUpdate = "UPDATE requerimiento_codigos SET contador = contador + 1 WHERE year = ? AND tipo = ?";
        jdbcTemplate.update(sqlUpdate, year, tipo);

        return resultados.get(0) + 1;
    }
}