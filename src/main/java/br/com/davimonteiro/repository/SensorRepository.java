package br.com.davimonteiro.repository;

import br.com.davimonteiro.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

    @Query(value = "select s from Sensor s join fetch s.metrics where s.id = :id")
    Optional<Sensor> findSensorBy(@Param("id") Long id);

}
