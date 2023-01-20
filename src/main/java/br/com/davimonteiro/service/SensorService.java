package br.com.davimonteiro.service;

import br.com.davimonteiro.api.dto.SensorDTO;
import br.com.davimonteiro.domain.Metric;
import br.com.davimonteiro.domain.Sensor;
import br.com.davimonteiro.exception.SensorNotFoundException;
import br.com.davimonteiro.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summarizingDouble;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

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
        List<Sensor> sensors = sensorRepository.querySensorsWithCreationDateBefore(sensorIds, metrics, creationDate);

        // Remove items
        sensors.forEach(sensor -> {
            Set<Metric> filteredMetrics = sensor.getMetrics()
                    .stream()
                    .filter(metric -> metrics.contains(metric.getName()))
                    .collect(Collectors.toSet());
            sensor.setMetrics(filteredMetrics);
        });

        List<SensorDTO> result = new ArrayList<>();

        sensors.forEach(sensor -> {
            Map<String, DoubleSummaryStatistics> summaryStatistics = sensor.getMetrics()
                    .stream()
                    .collect(groupingBy(Metric::getName, summarizingDouble(Metric::getValue)));

            SensorDTO dto = new SensorDTO();
            dto.setSensorId(sensor.getId());
            dto.setSummaryStatistics(summaryStatistics);
            result.add(dto);
        });

        return result;
    }

    public Sensor findById(Long id) throws SensorNotFoundException {
        return sensorRepository.findById(id).orElseThrow(SensorNotFoundException::new);
    }

}
