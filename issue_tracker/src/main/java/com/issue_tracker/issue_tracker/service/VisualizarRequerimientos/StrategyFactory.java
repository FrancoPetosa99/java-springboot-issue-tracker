package com.issue_tracker.issue_tracker.service.VisualizarRequerimientos;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class StrategyFactory {

    private static final Map<String, Supplier<SearchStrategy>> strategies = new HashMap<>();

    static {
        strategies.put("EXTERNO", SearchExternoStrategy::new);
        strategies.put("INTERNO", SearchInternoStrategy::new);
    }

    public static SearchStrategy createSearchStrategy(String userType) {
        Supplier<SearchStrategy> strategySupplier = strategies.get(userType.toUpperCase());
        return strategySupplier.get();
    }
}