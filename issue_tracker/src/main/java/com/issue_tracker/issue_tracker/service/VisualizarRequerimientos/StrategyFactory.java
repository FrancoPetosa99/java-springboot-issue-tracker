package com.issue_tracker.issue_tracker.service.VisualizarRequerimientos;

import java.util.HashMap;
import java.util.Map;

import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;

public class StrategyFactory {

    private static final Map<String, Class<? extends SearchStrategy>> strategies = new HashMap<>();

    static {
        strategies.put("EXTERNO", SearchExternoStrategy.class);
        strategies.put("INTERNO", SearchInternoStrategy.class);
    }

    public static SearchStrategy getStrategyClass(String userType, RequerimientoRepository repository) {
        try {
         
            Class<? extends SearchStrategy> strategyClass = strategies.get(userType.toUpperCase());
            
            if (strategyClass == null) 
                throw new IllegalArgumentException("No existe estrategia para el tipo de usuario: " + userType);
                
            return strategyClass.getDeclaredConstructor(RequerimientoRepository.class).newInstance(repository);

        } catch (Exception e) {
            throw new IllegalStateException("Error al crear instancia de estrategia para: " + userType, e);
        }
    }
}