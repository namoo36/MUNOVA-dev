package com.space.munovachattest.service;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Data
public class MetricsService {

    private final List<Map<String, Object>> results = new ArrayList<>();

    public void add(Map<String, Object> r) {
        results.add(r);
    }

    public Map<String, Object> summary() {
        int total = results.size();

        return Map.of(
                "bots", total,
                "avg_apiLatency", avg("apiLatency"),
                "avg_connect", avg("connectTime"),
                "avg_join", avg("joinLatency")
        );
    }

    private double avg(String key) {
        return results.stream()
                .mapToDouble(r -> (double) r.getOrDefault(key, 0))
                .average().orElse(0);
    }
}
