package br.com.davimonteiro.api;

import br.com.davimonteiro.api.dto.SensorDTO;
import br.com.davimonteiro.domain.Sensor;
import br.com.davimonteiro.exception.SensorNotFoundException;
import br.com.davimonteiro.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/api/sensors")
public class SensorResource {

    @Autowired
    private SensorService sensorService;

    /**
     *
     */
    @GetMapping(path = "/")
    public ResponseEntity query(@RequestParam List<Long> sensorIds,
                                @RequestParam List<String> metrics,
                                @RequestParam String statistic,
                                @RequestParam LocalDate creationDate) {
        List<SensorDTO> sensors = sensorService.query(sensorIds, metrics, statistic, creationDate);
        return ok(sensors);
    }

    @GetMapping(path = "/all")
    public ResponseEntity findAll() {
        return ok(sensorService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sensor> update(@PathVariable Long id, @RequestBody Sensor sensor)
            throws SensorNotFoundException {
        return ok(sensorService.update(id, sensor));
    }

}
