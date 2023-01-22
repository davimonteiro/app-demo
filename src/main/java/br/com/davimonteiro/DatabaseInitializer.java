package br.com.davimonteiro;

import br.com.davimonteiro.domain.Metric;
import br.com.davimonteiro.domain.Sensor;
import br.com.davimonteiro.repository.SensorRepository;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Initialize and populate the local database.
 *
 * @author Davi Monteiro
 */
@Service
public class DatabaseInitializer {

    public final SensorRepository sensorRepository;

    @Autowired
    public DatabaseInitializer(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void populate() {
        Sensor sensor1 = new Sensor();
        sensor1.setId(1L);
        Sensor sensor2 = new Sensor();
        sensor2.setId(2L);
        Sensor sensor3 = new Sensor();
        sensor3.setId(3L);

        for (int i = 0; i < 10; i++) {
            sensor1.setMetrics(createMetrics(sensor1));
            sensor2.setMetrics(createMetrics(sensor2));
            sensor3.setMetrics(createMetrics(sensor3));

            sensorRepository.save(sensor1);
            sensorRepository.save(sensor2);
            sensorRepository.save(sensor3);
        }
    }

    private Set<Metric> createMetrics(Sensor sensor) {
        LocalDate start = LocalDate.now().minusDays(30);
        LocalDate end = LocalDate.now();
        LocalDate randomDate = between(start, end);

        Metric temperature = new Metric.MetricBuilder()
                .name("Temperature")
                .value(generateRandomValue(-30.0, 30.0))
                .unit("Celsius")
                .creationDate(randomDate)
                .sensor(sensor)
                .build();

        Metric humidity = new Metric.MetricBuilder()
                .name("Humidity")
                .value(generateRandomValue(0.0, 100.0))
                .unit("%")
                .creationDate(randomDate)
                .sensor(sensor)
                .build();

        Metric windSpeed = new Metric.MetricBuilder()
                .name("Wind speed")
                .value(generateRandomValue(0.0, 50.0))
                .unit("km/h")
                .creationDate(randomDate)
                .sensor(sensor)
                .build();

        return Sets.newHashSet(temperature, humidity, windSpeed);
    }

    private Double generateRandomValue(Double rangeMin, Double rangeMax) {
        Random r = new Random();
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }
    private static LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
        return LocalDate.ofEpochDay(randomDay);
    }


}
