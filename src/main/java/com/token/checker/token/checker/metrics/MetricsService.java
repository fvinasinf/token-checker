package com.token.checker.token.checker.metrics;

import com.token.checker.token.checker.metrics.beans.Metrics;

public interface MetricsService {
    /**
     * Adds one more petition processed
     */
    void pettitionProcessed();
    /**
     * Gets the metrics of the microservice
     * @return metrics of the microservice
     */
    Metrics getMetrics();
}
