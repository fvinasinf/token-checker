package com.token.checker.token.checker.metrics;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.test.context.SpringBootTest;

import com.token.checker.token.checker.metrics.beans.Metrics;
import com.token.checker.token.checker.metrics.imp.MetricsServiceImpl;

@SpringBootTest()
@ExtendWith(MockitoExtension.class)
public class MetricsServiceTest {

    @InjectMocks
    private MetricsServiceImpl metricService;

    @Test
    public void pettitionProcessed() {
        metricService.pettitionProcessed();
        Metrics metrics = metricService.getMetrics();

        assertEquals(1, metrics.getPetitionsProcessed(), "Counting not equal");
    }

    @Test
    public void twoPettitionsProcessed() {
        metricService.pettitionProcessed();
        metricService.pettitionProcessed();
        Metrics metrics = metricService.getMetrics();

        assertEquals(2, metrics.getPetitionsProcessed(), "Counting not equal");
    }
}
