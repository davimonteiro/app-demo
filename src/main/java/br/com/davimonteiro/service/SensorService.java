package br.com.davimonteiro.service;

import br.com.davimonteiro.api.dto.SensorDTO;
import br.com.davimonteiro.domain.Metric;
import br.com.davimonteiro.domain.Sensor;
import br.com.davimonteiro.exception.SensorNotFoundException;
import br.com.davimonteiro.repository.MetricRepository;
import br.com.davimonteiro.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summarizingDouble;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private MetricRepository metricRepository;

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    @Transactional
    public Sensor update(Long id, Sensor sensor) throws SensorNotFoundException {
        return sensorRepository.findById(id).map(currentSensor -> {
            sensor.getMetrics().forEach(metric -> {
                currentSensor.getMetricById(metric.getId())
                        .setValue(metric.getValue());
            });
            return sensorRepository.save(currentSensor);
        }).orElseThrow(SensorNotFoundException::new);
    }

    public List<SensorDTO> query(List<Long> sensorIds, List<String> metrics, String statistic, LocalDate creationDate) {
        List<Metric> listOfMetrics = metricRepository.queryMetricsWithCreationDateBefore(sensorIds, metrics, creationDate);

        List<SensorDTO> result = new ArrayList<>();
        sensorIds.forEach(sensorId -> {
            Map<String, DoubleSummaryStatistics> summaryStatistics = listOfMetrics.stream()
                    .filter(metric -> metric.getSensor().getId().equals(sensorId))
                    .collect(groupingBy(Metric::getName, summarizingDouble(Metric::getValue)));

            SensorDTO dto = new SensorDTO();
            dto.setSensorId(sensorId);
            summaryStatistics.forEach((metricName, summary) -> dto.setSummaryStatistics(metricName, summary, statistic));
            result.add(dto);
        });

        return result;
    }

    public Sensor findById(Long id) throws SensorNotFoundException {
        return sensorRepository.findSensorBy(id)
                .orElseThrow(SensorNotFoundException::new);
    }

}
