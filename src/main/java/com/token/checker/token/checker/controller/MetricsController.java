package com.token.checker.token.checker.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.token.checker.token.checker.logging.LoggingService;
import com.token.checker.token.checker.metrics.MetricsService;
import com.token.checker.token.checker.metrics.beans.Metrics;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/** 
 * For metrics, we will expose an endpoint dedicated to this.
 * The external consumer service can retrieve the metrics calling it
 * as a JSON object, and can be expandable easely adding more fields to the
 * Metrics java class. The only metric we have is 'petittions processed',
 * which is incremented on each call.
 */
@Tag(name="Metrics", description = "Allows to get the metrics of the token endpoint")
@RestController
@RequestMapping("${metrics.path}")
public class MetricsController {

    private MetricsService metricsService;
    private LoggingService loggingService;

    public MetricsController(MetricsService metricsService, LoggingService loggingService) {
        this.metricsService = metricsService;
        this.loggingService = loggingService;
    }

    @Operation(summary = "Get metrics", description = "Gets the metrics of the token service")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mettrics acquired")
    })
    @GetMapping(value="", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Metrics> getMetrics() {
        loggingService.info("Metrics checked");
        return ResponseEntity.ok(metricsService.getMetrics());
    }

}
