package br.com.davimonteiro.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;

@Getter @Setter
@Entity
public class Sensor {
    @Id
    private Long id;

    @JsonManagedReference
    @OneToMany(mappedBy = "sensor", cascade = ALL, fetch = EAGER)
    private Set<Metric> metrics = new HashSet<>();

    public Metric getMetricById(Long id) {
        return metrics.stream()
                .filter(metric -> metric.getId().equals(id))
                .findFirst().get();
    }

}
