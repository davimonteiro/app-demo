package br.com.davimonteiro.api.dto;

import lombok.Data;

import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.Map;

@Data
public class SensorDTO {

    private Long sensorId;
    Map<String, SummaryStatistics> summaryStatistics = new HashMap<>();

    public void setSummaryStatistics(String metricName, DoubleSummaryStatistics summary, String statistic) {
        SummaryStatistics statistics = new SummaryStatistics();

        switch (statistic) {
            case "min":
                statistics.setMin(summary.getMin());
                break;
            case "max":
                statistics.setMax(summary.getMax());
                break;
            case "sum":
                statistics.setSum(summary.getSum());
                break;
            case "average":
                statistics.setAverage(summary.getAverage());
                break;
        }

        summaryStatistics.put(metricName, statistics);
    }

}


@Data
class SummaryStatistics {
    private Double sum;
    private Double min;
    private Double max;
    private Double average;

}

