package com.token.checker.token.checker.metrics.imp;

import org.springframework.stereotype.Service;

import com.token.checker.token.checker.metrics.MetricsService;
import com.token.checker.token.checker.metrics.beans.Metrics;

@Service
public class MetricsServiceImpl implements MetricsService {

    private Metrics metrics;

    public MetricsServiceImpl() {
        this.metrics = new Metrics();
        this.metrics.setPetitionsProcessed(0);
    }

    @Override
    public void pettitionProcessed() {
        this.metrics.setPetitionsProcessed(this.metrics.getPetitionsProcessed() + 1);
    }

    @Override
    public Metrics getMetrics() {
        return this.metrics;
    }

}
