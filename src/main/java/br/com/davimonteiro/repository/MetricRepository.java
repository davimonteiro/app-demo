package br.com.davimonteiro.repository;

import br.com.davimonteiro.domain.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MetricRepository extends JpaRepository<Metric, Long> {

    @Query(value = "select m from Metric m where m.name in :metrics and m.sensor.id in :sensors and m.creationDate <= :date")
    List<Metric> queryMetricsWithCreationDateBefore(@Param("sensors") List<Long> sensors, @Param("metrics") List<String> metrics, @Param("date") LocalDate date);

}
