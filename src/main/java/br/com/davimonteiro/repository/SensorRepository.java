package br.com.davimonteiro.repository;

import br.com.davimonteiro.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

    @Query(value = "select s from Sensor s join Metric m on s.id = m.sensor.id where s.id in (:ids) and m.name in (:metrics) and m.creationDate <= :date")
    List<Sensor> querySensorsWithCreationDateBefore(@Param("ids") List<Long> ids,
                                                    @Param("metrics") List<String> metrics,
                                                    @Param("date") LocalDate date);

}
