package br.com.davimonteiro.api.dto;

import lombok.Data;

import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.Map;

@Data
public class SensorDTO {

    private Long sensorId;
    Map<String, DoubleSummaryStatistics> summaryStatistics = new HashMap<>();

}

